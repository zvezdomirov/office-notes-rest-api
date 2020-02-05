package com.officenotes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true,
        exclude = {"teams"})
@NoArgsConstructor
public class User extends BaseEntity {
    private String username;

    private String password;

    private String role;

    @OneToMany
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private Set<Team> teams;

    @Builder
    User(String username, String password,
         String role, Set<Team> teams) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.teams = teams;
    }

    @Transient
    @JsonIgnore
    public boolean isInATeam() {
        return this.teams != null && this.teams.size() > 0;
    }
}


