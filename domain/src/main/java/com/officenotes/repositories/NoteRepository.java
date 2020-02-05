package com.officenotes.repositories;

import com.officenotes.models.Note;
import com.officenotes.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {
    List<Note> findAllByTeamIsNull(Pageable pageable);

    List<Note> findAllByCreatedBy(User creator);

    Long countAllByDeadlineAfterAndTeamIsNull(LocalDateTime deadline);

    Long countAllByDeadlineAfterAndTeam_Name(LocalDateTime deadline, String teamName);
}
