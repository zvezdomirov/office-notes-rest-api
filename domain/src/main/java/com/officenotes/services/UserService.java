package com.officenotes.services;

import com.officenotes.exceptions.PasswordMismatchException;
import com.officenotes.exceptions.UserAlreadyTakenException;
import com.officenotes.dtos.UserDto;
import com.officenotes.dtos.UserRoleDto;
import com.officenotes.enums.TeamRoles;
import com.officenotes.models.Team;
import com.officenotes.models.User;
import com.officenotes.repositories.TeamRepository;
import com.officenotes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder encoder;

    /**
     * Used to register a new user in the database.
     *
     * @param registrationForm A DTO, holding the data, needed for registration.
     * @return The newly registered user.
     */
    public User registerUser(UserDto registrationForm) {
        Optional<User> dbUser = this.userRepository.findByUsername(registrationForm.getUsername());
        if (dbUser.isPresent()) {
            throw new UserAlreadyTakenException(registrationForm.getUsername());
        } else if (!registrationForm.getPassword().equalsIgnoreCase(
                registrationForm.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }
        User userToRegister = User.builder()
                .username(registrationForm.getUsername())
                .password(this.encoder.encode(registrationForm.getPassword()))
                .role(TeamRoles.DEVELOPER.name())
                .teams(new HashSet<>())
                .build();
        return this.userRepository.save(userToRegister);
    }

    /**
     * Used to change a user's role. (e.g. from "developer" to "manager")
     *
     * @param userDto A DTO, holding the target user's
     *                username and his new role.
     * @return The updated user.
     */
    public User changeUserRole(UserRoleDto userDto) {
        User foundUser = this.findByUsername(userDto.getUsername());
        foundUser.setRole(userDto.getRole().toUpperCase());
        Set<Team> userTeams = foundUser.getTeams();
        userTeams
                .forEach(team -> {
                    team.getDevelopers().remove(foundUser);
                    this.teamRepository.save(team);
                });
        return this.userRepository.save(foundUser);
    }

    User findByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User loginUser(UserDto userDto) {
        User loggedUser = this.findByUsername(userDto.getUsername());
        if (this.encoder.matches(userDto.getPassword(), loggedUser.getPassword())) {
            return loggedUser;
        }
        throw new PasswordMismatchException("Wrong password.");
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
