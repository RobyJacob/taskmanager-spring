package com.example.taskmanager.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewTaskRequest {
    private String title;

    private String description;

    private String dueDate;

    private String completed;
}
