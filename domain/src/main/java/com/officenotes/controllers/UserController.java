package com.officenotes.controllers;

import com.officenotes.dtos.UserDto;
import com.officenotes.dtos.UserRoleDto;
import com.officenotes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService.registerUser(dto));
    }

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.loginUser(userDto));
    }

    @PutMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> changeUserRole(
            @RequestBody UserRoleDto userDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.changeUserRole(userDto));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.getAllUsers());
    }
}
