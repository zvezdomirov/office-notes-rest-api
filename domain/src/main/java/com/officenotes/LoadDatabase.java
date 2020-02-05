package com.officenotes;

import com.officenotes.models.User;
import com.officenotes.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@Slf4j
class LoadDatabase {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "0000";

    @Bean
    CommandLineRunner preloadAdmin(UserRepository userRepository,
                                   PasswordEncoder encoder) {
        if (!userRepository.findByUsername(ADMIN_USERNAME).isPresent()) {
            User admin = User.builder()
                    .username(ADMIN_USERNAME)
                    .password(encoder.encode(ADMIN_PASSWORD))
                    .role("ADMIN")
                    .teams(Collections.emptySet())
                    .build();
            userRepository.save(admin);
        }
        return args -> log.info(String.format(
                "Preloading default admin with username: %s and password:%s%n",
                ADMIN_USERNAME,
                ADMIN_PASSWORD));
    }

//    @Bean
//    CommandLineRunner preloadTeams(TeamRepository teamRepository,
//                                   UserRepository userRepository,
//                                   NoteRepository noteRepository) {
//        List<User> users = userRepository.findAll();
//        List<Note> notes = noteRepository.findAll();
//        List<Team> teams = teamRepository.findAll();
//        if (users.isEmpty()) {
//            users = this.generateUsers(10);
//        }
//        if (notes.isEmpty()) {
//            notes = this.generateNotes(20, users);
//        }
//        if (teams.isEmpty()) {
//            teams = this.generateTeams(2, users, notes);
//        }
//        return args -> log.info("Preloaded database with users, notes and teams.");
//    }
//
//    private List<Note> generateNotes(int numOfNotes, List<User> users) {
//        List<Note> newNotes = new ArrayList<>();
//        for (int i = 0; i < numOfNotes; i++) {
//            Note current = new Note(
//                    "Note" + i,
//                    "Nice Description" + i,
//                    LocalDateTime.now());
//
//            newNotes.add(current);
//        }
//        return newNotes;
//    }
//
//    private List<User> generateUsers(int numOfUsers) {
//        List<User> newUsers = new ArrayList<>();
//        TeamRoles[] availableRoles = TeamRoles.values();
//        for (int i = 0; i < numOfUsers; i++) {
//            User current = User.builder()
//                    .username("Gosho" + i)
//                    .password("123")
//                    .role(availableRoles[i % availableRoles.length].name())
//                    .build();
//            newUsers.add(current);
//        }
//        return newUsers;
//    }
}
