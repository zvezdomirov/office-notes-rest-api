package com.officenotes.controllers;

import com.officenotes.dtos.NoteDto;
import com.officenotes.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
class NoteController {
    private final NoteService noteService;

    @GetMapping("/notes/company")
    @PreAuthorize("hasAnyRole('MANAGER', 'DEVELOPER')")
    ResponseEntity getCompanyNotes(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(this.noteService
                .getCompanyNotes(page, limit));
    }

    @GetMapping("/notes/company/count")
    @PreAuthorize("hasAnyRole('MANAGER', 'DEVELOPER')")
    ResponseEntity getNonExpiredCompanyNotesCount() {
        return ResponseEntity.ok(
                this.noteService.getNonExpiredCompanyNotesCount());
    }

    @GetMapping("/notes/team/{teamName}/count")
    @PreAuthorize("hasAnyRole('MANAGER', 'DEVELOPER')")
    ResponseEntity getNonExpiredTeamNotesCount(@PathVariable String teamName) {
        return ResponseEntity.ok(
                this.noteService.getNonExpiredTeamNotesCount(teamName));
    }

    @PostMapping("/notes/company")
    @PreAuthorize("hasAnyRole('MANAGER', 'DEVELOPER')")
    ResponseEntity<?> createCompanyNote(@RequestBody NoteDto noteDto,
                                        Principal principal) {
        String creatorUsername = principal.getName();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.noteService
                        .createCompanyNote(noteDto, creatorUsername));
    }

    @PostMapping("/notes/team/{teamName}")
    @PreAuthorize("hasAnyRole('MANAGER', 'DEVELOPER')")
    ResponseEntity<?> createTeamNote(@RequestBody NoteDto noteDto,
                                     @PathVariable String teamName,
                                     Principal principal) {
        String creatorUsername = principal.getName();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.noteService
                        .createTeamNote(noteDto, creatorUsername, teamName));
    }

    @GetMapping("/notes/team")
    @PreAuthorize("hasAnyRole('MANAGER', 'DEVELOPER')")
    ResponseEntity<?> getTeamNotes(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(
                this.noteService.getTeamNotes(username));
    }

    @GetMapping("/notes/my")
    @PreAuthorize("hasAnyRole('MANAGER', 'DEVELOPER')")
    ResponseEntity<?> getOwnNotes(Principal principal) {
        String currentUsername = principal.getName();
        return ResponseEntity.ok(
                this.noteService.getOwnNotes(currentUsername));
    }

    @PutMapping("/notes/my/{noteId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'DEVELOPER')")
    ResponseEntity<?> editOwnNote(@PathVariable String noteId,
                                  @RequestBody NoteDto editedNote,
                                  Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(
                this.noteService.editOwnNote(editedNote, username, noteId));
    }

    @PutMapping("/notes/team/{teamName}/{noteId}")
    @PreAuthorize("hasRole('MANAGER')")
    ResponseEntity<?> editTeamNote(@PathVariable String teamName,
                                   @PathVariable String noteId,
                                   @RequestBody NoteDto editedNote,
                                   Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok(
                this.noteService.editTeamNote(editedNote, username, teamName, noteId));
    }

    @DeleteMapping("/notes/my/{noteId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'DEVELOPER')")
    ResponseEntity<?> deleteOwnNote(@PathVariable String noteId,
                                    Principal principal) {
        String username = principal.getName();
        this.noteService.deleteOwnNote(username, noteId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/notes/team/{teamName}/{noteId}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    ResponseEntity<?> deleteTeamNote(@PathVariable String teamName,
                                     @PathVariable String noteId,
                                     Principal principal) {
        String username = principal.getName();
        this.noteService.deleteTeamNote(username, teamName, noteId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
