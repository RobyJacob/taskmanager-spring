package com.example.taskmanager.repositories;

import com.example.taskmanager.entities.NoteEntity;
import com.example.taskmanager.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {
    @Transactional
    void deleteByTaskEntityAndId(TaskEntity taskEntity, Integer id);

    @Transactional
    void deleteAllByTaskEntity(TaskEntity task);

    List<NoteEntity> findAllByTaskEntity(TaskEntity task);
}
