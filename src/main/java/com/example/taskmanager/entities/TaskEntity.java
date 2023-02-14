package com.example.taskmanager.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity(name = "tasks")
@Getter
@Setter
public class TaskEntity extends BaseEntity {
    @Column(name = "description", nullable = false, length = 300)
    private String description;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "due_date", nullable = true)
    private LocalDate dueDate;

    @Column(name = "completed")
    private Boolean completed = false;
}
