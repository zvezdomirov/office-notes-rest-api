package com.officenotes.dtos;

import lombok.Data;

@Data

public class UserDto {
    private String username;
    private String password;
    private String confirmPassword;
}
