package com.example.taskmanager.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NoteResponse {
    private Integer id;

    private String body;

    private LocalDate createdAt;
}
