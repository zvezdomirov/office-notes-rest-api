package com.officenotes.models;

import com.officenotes.enums.TeamRoles;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "teams")
@Data
@EqualsAndHashCode(callSuper = true,
        exclude = {"developers", "notes", "manager"})
@NoArgsConstructor
public class Team extends BaseEntity {
    @Column(name = "team_name")
    private String name;

    @OneToOne
    private User manager;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<User> developers;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "team")
    private Set<Note> notes;

    @Builder
    public Team(String name, User manager,
                Set<User> developers,
                Set<Note> notes) {
        this.name = name;
        this.manager = manager;
        this.developers = developers;
        this.notes = notes;
    }

    public Set<User> addDevelopers(User... developers) {
        // Get only the users which are developers
        Set<User> addedDevelopers = Arrays.stream(developers)
                .filter(user -> user.getRole().equalsIgnoreCase(TeamRoles.DEVELOPER.name()))
                .collect(Collectors.toSet());
        this.developers.addAll(addedDevelopers);
        return addedDevelopers;
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }
}
