package com.officenotes.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDto {
    private String title;
    private String description;
    private LocalDateTime deadline;
}
