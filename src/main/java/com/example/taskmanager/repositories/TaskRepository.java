package com.example.taskmanager.repositories;

import com.example.taskmanager.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    List<TaskEntity> findAllByCompleted(Boolean completed);

    List<TaskEntity> findAllByDueDate(LocalDate date);
}
