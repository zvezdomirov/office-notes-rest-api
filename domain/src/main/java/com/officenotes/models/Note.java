package com.officenotes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTES")
@Data
@EqualsAndHashCode(callSuper = true,
        exclude = {"team", "createdBy"})
@NoArgsConstructor
public class Note extends BaseEntity {
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    @JsonIgnore
    @ManyToOne
    private Team team;

    @ManyToOne
    private User createdBy;

    public Note(String title,
                String description,
                LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    @PrePersist
    public void createdAt() {
        this.createdAt = LocalDateTime.now();
    }
}
