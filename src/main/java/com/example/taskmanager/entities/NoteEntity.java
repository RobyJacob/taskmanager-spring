package com.example.taskmanager.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "notes")
@Getter
@Setter
public class NoteEntity extends BaseEntity {
    @Column(name = "body", nullable = false, length = 1000)
    private String body;

    @ManyToOne
    TaskEntity taskEntity;
}
