package com.officenotes.repositories;

import com.officenotes.models.Team;
import com.officenotes.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, String> {
    Optional<Team> findByName(String teamName);

    List<Team> findAllByManager(User user);

    Optional<Team> findByDevelopersContaining(User user);

    List<Team> findAll();
}
