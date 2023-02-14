package com.example.taskmanager.repositories;

import com.example.taskmanager.entities.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class TaskRepositoryTests {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testCreateTask() {
        TaskEntity taskEntity = new TaskEntity();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        taskEntity.setTitle("test title1");
        taskEntity.setDescription("sample description");
        taskEntity.setDueDate(LocalDate.parse("2023-02-11", format));

        var savedTask = taskRepository.save(taskEntity);

        assertNotNull(savedTask);
        assertEquals(false, savedTask.getCompleted());
        assertEquals(LocalDate.parse("2023-02-11", format), savedTask.getDueDate());

        assertEquals(LocalDate.now(), savedTask.getCreatedAt());
    }

    @Test
    public void testFindAllByCompleted() {
        TaskEntity task1 = new TaskEntity();
        task1.setCompleted(false);
        task1.setTitle("sample 1");
        task1.setDescription("sample description 1");

        TaskEntity task2 = new TaskEntity();
        task2.setCompleted(true);
        task2.setTitle("sample 2");
        task2.setDescription("sample description 2");

        taskRepository.saveAll(List.of(task1, task2));

        assertEquals(1, taskRepository.findAllByCompleted(true).size());
        assertEquals(1, taskRepository.findAllByCompleted(false).size());
    }

    @Test
    public void testFindAllByDueDate() {
        TaskEntity task1 = new TaskEntity();
        task1.setCompleted(false);
        task1.setTitle("sample 1");
        task1.setDescription("sample description 1");
        task1.setDueDate(LocalDate.of(2023, 02, 01));

        TaskEntity task2 = new TaskEntity();
        task2.setCompleted(true);
        task2.setTitle("sample 2");
        task2.setDescription("sample description 2");
        task2.setDueDate(LocalDate.of(2023, 02, 11));

        taskRepository.saveAll(List.of(task1, task2));

        assertEquals(1, taskRepository.findAllByDueDate(LocalDate.of(2023, 02, 01))
                .size());
        assertEquals(1, taskRepository.findAllByDueDate(LocalDate.of(2023, 02, 11))
                .size());
        assertEquals(0, taskRepository.findAllByDueDate(LocalDate.of(2023, 02, 10))
                .size());
    }
}
