package com.example.taskmanager.repositories;

import com.example.taskmanager.entities.NoteEntity;
import com.example.taskmanager.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {
    void deleteByTaskEntityAndId(TaskEntity task, Integer noteId);

    List<NoteEntity> findAllByTaskEntity(TaskEntity task);
}
