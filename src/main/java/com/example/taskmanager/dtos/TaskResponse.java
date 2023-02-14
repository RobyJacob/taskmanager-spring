package com.example.taskmanager.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TaskResponse {
    private Integer id;

    private String title;

    private String description;

    private LocalDate dueDate;

    private Boolean completed;

    private LocalDate createdAt;

    private List<NoteResponse> notes;
}
