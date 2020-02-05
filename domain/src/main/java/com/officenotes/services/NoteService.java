package com.officenotes.services;

import com.officenotes.exceptions.NoteNotFoundException;
import com.officenotes.exceptions.UserNotInTeamException;
import com.officenotes.dtos.NoteDto;
import com.officenotes.exceptions.NoteOwnershipException;
import com.officenotes.models.Note;
import com.officenotes.models.Team;
import com.officenotes.models.User;
import com.officenotes.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;
    private final TeamService teamService;

    /**
     * Used to retrieve all the notes,
     * that are not in a specific team,
     * a.k.a. the public ones. (with option for pagination)
     *
     * @param page  The page to retrieve.
     * @param limit The maximum number of notes per page.
     * @return A list, containing notes that are not in a specific team.
     */
    public List<Note> getCompanyNotes(int page, int limit) {
        Pageable paging = PageRequest.of(page, limit);
        return this.noteRepository.findAllByTeamIsNull(paging);
    }

    /**
     * Used to retrieve all the notes
     * in the current user's team.
     *
     * @param username Current user's username.
     * @return A set, containing the notes in the current user's team
     */
    public List<Note> getTeamNotes(String username) {
        User currentUser = this.userService.findByUsername(username);
        List<Team> teams;
        List<Note> allTeamsNotes = new ArrayList<>();
        if (currentUser.getRole().equalsIgnoreCase("manager")) {
            teams = this.teamService.findAllByManager(currentUser);
        } else {
            teams = Collections.singletonList(
                    this.teamService.findByDeveloper(currentUser));
        }
        if (teams == null || teams.isEmpty()) {
            throw new UserNotInTeamException(username);
        }
        for (Team team : teams) {
            if (!team.getDevelopers().contains(currentUser) &&
                    !team.getManager().equals(currentUser)) {
                throw new UserNotInTeamException(username, team.getName());
            }
            allTeamsNotes.addAll(team.getNotes());
        }
        return allTeamsNotes;
    }

    /**
     * Used to create and persist a note, that the whole company
     * (Users from all teams) is able to see.
     *
     * @param noteDto         Data for the new note.
     * @param creatorUsername The username of the creator.
     * @return The created and persisted note.
     */
    public Note createCompanyNote(NoteDto noteDto, String creatorUsername) {
        User creator = this.userService.findByUsername(creatorUsername);
        Note createdNote = new Note(
                noteDto.getTitle(),
                noteDto.getDescription(),
                noteDto.getDeadline());
        createdNote.setCreatedBy(creator);
        createdNote.setTeam(null);
        return this.noteRepository.save(createdNote);
    }

    /**
     * Used to create and persist a note, that only users
     * within a certain team are able to see.
     *
     * @param noteDto  Data for the new note.
     * @param teamName The name of the team, that will hold the new note.
     * @param creatorUsername The username of the note's creator.
     * @return A set of all current team's notes.
     */
    public Note createTeamNote(NoteDto noteDto, String creatorUsername, String teamName) {
        User creator = this.userService.findByUsername(creatorUsername);
        Team team = this.teamService.findByName(teamName);
        if (!team.getDevelopers().contains(creator) &&
                team.getManager() != creator) {
            throw new UserNotInTeamException(creator.getUsername(), teamName);
        }
        Note createdNote = new Note(
                noteDto.getTitle(),
                noteDto.getDescription(),
                noteDto.getDeadline());
        createdNote.setCreatedBy(creator);
        team.addNote(createdNote);
        createdNote.setTeam(team);
        return this.noteRepository.save(createdNote);
    }

    /**
     * Used by the current user to edit one of the
     * notes he is a creator of.
     *
     * @param noteId   Id of the edited note.
     * @param noteDto  DTO, containing the edited note data.
     * @param username Username of the editor.
     * @return The edited note.
     */
    public Note editOwnNote(NoteDto noteDto, String username, String noteId) {
        User noteOwner = this.userService.findByUsername(username);
        Note noteToEdit = findById(noteId);
        verifyNoteOwner(noteOwner, noteToEdit);
        noteToEdit.setDeadline(noteDto.getDeadline());
        noteToEdit.setDescription(noteDto.getDescription());
        noteToEdit.setTitle(noteDto.getTitle());
        return this.noteRepository.save(noteToEdit);
    }

    /**
     * Used by users with the privileges(usually managers)
     * to edit a certain note in a team.
     *
     * @param teamName Name of the team, holding the note.
     * @param noteDto  DTO, containing the edited note data.
     * @param username Username of the editor.
     * @param noteId   Id of the edited note.
     * @return The edited note.
     */
    public Note editTeamNote(NoteDto noteDto, String username,
                             String teamName, String noteId) {
        User editor = this.userService.findByUsername(username);
        Note noteToEdit = findById(noteId);
        Team noteTeam = this.teamService.findByName(teamName);
        verifyNoteBelongsToTeam(noteToEdit, noteTeam);
        verifyNoteIsInManagerTeam(editor, noteToEdit);
        noteToEdit.setDeadline(noteDto.getDeadline());
        noteToEdit.setDescription(noteDto.getDescription());
        noteToEdit.setTitle(noteDto.getTitle());
        return this.noteRepository.save(noteToEdit);
    }

    /**
     * Used by the current user to delete one of the
     * notes he is a creator of.
     *
     * @param username Username of the user, deleting the note.
     * @param noteId   Id of the deleted note.
     */
    public void deleteOwnNote(String username, String noteId) {
        User noteOwner = this.userService.findByUsername(username);
        Note noteToDelete = findById(noteId);
        verifyNoteOwner(noteOwner, noteToDelete);
        this.noteRepository.delete(noteToDelete);
    }

    /**
     * Used by users with the privileges(usually managers)
     * to delete a certain note in a team.
     *
     * @param teamName Name of the team, holding the note.
     * @param username Username of the user, deleting the note.
     * @param noteId   Id of the deleted note.
     */
    public void deleteTeamNote(String username, String teamName, String noteId) {
        User noteOwner = this.userService.findByUsername(username);
        Note noteToDelete = findById(noteId);
        Team noteTeam = this.teamService.findByName(teamName);
        verifyNoteBelongsToTeam(noteToDelete, noteTeam);
        verifyNoteIsInManagerTeam(noteOwner, noteToDelete);
        this.noteRepository.delete(noteToDelete);
    }

    private Note findById(String noteId) {
        return this.noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException(noteId));
    }

    private void verifyNoteOwner(User noteOwner, Note note) {
        if (!note.getCreatedBy().equals(noteOwner)) {
            throw new NoteOwnershipException(
                    String.format("Note with id %s is not owned by user %s",
                            note.getId(),
                            noteOwner.getUsername())
            );
        }
    }

    private void verifyNoteBelongsToTeam(Note note, Team team) {
        if (!note.getTeam().equals(team)) {
            throw new NoteNotFoundException(String.format(
                    "Note with id %s is not in team %s",
                    note.getId(), team.getId())
            );
        }
    }

    private void verifyNoteIsInManagerTeam(User manager, Note note) {
        if (!note.getTeam().getManager().equals(manager)) {
            throw new NoteOwnershipException(String.format(
                    "Note %s is not in this user's team", note.getId()));
        }
    }

    public List<Note> getOwnNotes(String username) {
        User currentUser = this.userService.findByUsername(username);
        return this.noteRepository.findAllByCreatedBy(currentUser);
    }

    public Long getNonExpiredCompanyNotesCount() {
        return this.noteRepository
                .countAllByDeadlineAfterAndTeamIsNull(LocalDateTime.now());
    }

    public Long getNonExpiredTeamNotesCount(String teamName) {
        return this.noteRepository
                .countAllByDeadlineAfterAndTeam_Name(LocalDateTime.now(), teamName);

    }
}
