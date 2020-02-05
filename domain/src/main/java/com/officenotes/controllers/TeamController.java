package com.officenotes.controllers;

import com.officenotes.dtos.TeamDto;
import com.officenotes.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
class TeamController {
    private final TeamService teamService;

    @Autowired
    TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/team")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> addTeam(@RequestBody TeamDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.teamService.addTeam(dto));
    }

    @PostMapping("/team/{teamName}/manager")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> addManagerToTeam(
            @RequestParam String managerUsername,
            @PathVariable String teamName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.teamService.addManagerToTeam(
                        managerUsername, teamName));
    }

    @PostMapping("/team/{teamName}/developers")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> addDeveloperToTeam(
            @RequestParam String devUsername,
            @PathVariable String teamName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.teamService.addDeveloperToTeam(devUsername, teamName));
    }
}
