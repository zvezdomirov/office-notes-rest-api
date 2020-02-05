package com.officenotes.services;

import com.officenotes.exceptions.InvalidUserRoleException;
import com.officenotes.exceptions.TeamAlreadyHasAManagerException;
import com.officenotes.exceptions.TeamAlreadyPresentException;
import com.officenotes.exceptions.TeamNotFoundException;
import com.officenotes.exceptions.UserAlreadyHasATeamException;
import com.officenotes.exceptions.UserNotInTeamException;
import com.officenotes.dtos.TeamDto;
import com.officenotes.enums.TeamRoles;
import com.officenotes.exceptions.*;
import com.officenotes.models.Team;
import com.officenotes.models.User;
import com.officenotes.repositories.TeamRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserService userService;

    /**
     * Used to add a new team.
     *
     * @param teamDto A DTO, holding the data for the new team.
     * @return The new team.
     */
    public Team addTeam(TeamDto teamDto) {
        String teamName = teamDto.getTeamName();
        Optional<Team> foundTeam = this.teamRepository.findByName(teamName);
        if (foundTeam.isPresent()) {
            throw new TeamAlreadyPresentException(teamName);
        }
        Team newTeam = Team.builder()
                .name(teamName)
                .build();
        return this.teamRepository.save(newTeam);
    }

    /**
     * Used to add a User with the role "manager" to a team.
     * Keep in mind: A team can only have one manager, but
     * a manager can manage many teams.(These cases are handled)
     *
     * @param managerUsername Username of the new manager.
     * @param teamName        Name of the team, the new manager will be added to.
     * @return The updated Team.
     */
    public Team addManagerToTeam(String managerUsername, String teamName) {
        User foundUser = this.userService.findByUsername(managerUsername);
        this.verifyUserRole(TeamRoles.MANAGER.name(), foundUser.getRole());
        Team team = this.findByName(teamName);
        if (team.getManager() != null) {
            throw new TeamAlreadyHasAManagerException(
                    teamName, team.getManager().getUsername()
            );
        }
        team.setManager(foundUser);
        this.teamRepository.save(team);
        return team;
    }

    /**
     * Used to add a User with the role "developer" to a team.
     *
     * @param devUsername Username of the new developer.
     * @param teamName    Name of the team, the new developer will be added to.
     * @return The updated Team.
     */
    public Team addDeveloperToTeam(String devUsername, String teamName) {
        User foundUser = this.userService.findByUsername(devUsername);
        this.verifyUserRole(TeamRoles.DEVELOPER.name(), foundUser.getRole());
        if (foundUser.isInATeam()) {
            throw new UserAlreadyHasATeamException(
                    devUsername, foundUser.getTeams().toString());
        }
        Team team = this.findByName(teamName);
        team.addDevelopers(foundUser);
        this.teamRepository.save(team);
        return team;
    }

    /**
     * A delegator between the controller and the repository.
     *
     * @param team Team to be persisted.
     * @return The newly persisted team.
     */
    Team save(Team team) {
        return this.teamRepository.save(team);
    }

    private void verifyUserRole(String claimedRole, String actualRole) {
        if (!actualRole.equalsIgnoreCase(claimedRole)) {
            throw new InvalidUserRoleException(actualRole);
        }
    }

    Team findByName(String teamName) {
        return this.teamRepository.findByName(teamName)
                .orElseThrow(() -> new TeamNotFoundException(teamName));
    }

    List<Team> findAllByManager(User user) {
        return this.teamRepository.findAllByManager(user);
    }

    Team findByDeveloper(User user) {
        return this.teamRepository.findByDevelopersContaining(user)
                .orElseThrow(() -> new UserNotInTeamException(user.getUsername()));
    }
}
