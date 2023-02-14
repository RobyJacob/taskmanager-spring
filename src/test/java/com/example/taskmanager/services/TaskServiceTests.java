package com.example.taskmanager.services;

import com.example.taskmanager.entities.NoteEntity;
import com.example.taskmanager.entities.TaskEntity;
import com.example.taskmanager.repositories.NoteRepository;
import com.example.taskmanager.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TaskServiceTests {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NoteRepository noteRepository;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private List<TaskEntity> getTasks() {
        return taskRepository.findAll();
    }

    private TaskEntity getTaskById(Integer taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskService.TaskNotFoundException(taskId));
    }

    private TaskEntity addTask(TaskEntity task) {
        task.setDueDate(LocalDate.parse(task.getDueDate().format(dateFormat), dateFormat));

        return taskRepository.save(task);
    }

    @Test
    public void testGetAllTasks() {
        TaskEntity task1 = new TaskEntity();
        task1.setCompleted(false);
        task1.setTitle("sample 1");
        task1.setDescription("sample description 1");
        task1.setDueDate(LocalDate.of(2023, 2, 1));

        TaskEntity task2 = new TaskEntity();
        task2.setCompleted(true);
        task2.setTitle("sample 2");
        task2.setDescription("sample description 2");
        task2.setDueDate(LocalDate.of(2023, 2, 11));

        taskRepository.saveAll(List.of(task1, task2));

        assertEquals(2, getTasks().size());
    }

    @Test
    public void testGetTaskById() {
        TaskEntity task1 = new TaskEntity();
        task1.setCompleted(false);
        task1.setTitle("sample 1");
        task1.setDescription("sample description 1");
        task1.setDueDate(LocalDate.of(2023, 2, 1));

        var savedTask = taskRepository.save(task1);

        var note = new NoteEntity();
        note.setBody("sample note 1");
        note.setTaskEntity(task1);

        noteRepository.save(note);

        var fetchedTask = getTaskById(savedTask.getId());

        assertThat(savedTask).isEqualTo(fetchedTask);
    }

    @Test
    public void testAddTask() {
        TaskEntity task1 = new TaskEntity();
        task1.setCompleted(false);
        task1.setTitle("sample 1");
        task1.setDescription("sample description 1");
        task1.setDueDate(LocalDate.of(2023, 2, 1));

        var savedTask = addTask(task1);

        assertEquals(1, savedTask.getId());
        assertEquals("sample 1", savedTask.getTitle());
        assertEquals("sample description 1", savedTask.getDescription());
        assertEquals(false, savedTask.getCompleted());
        assertEquals(LocalDate.of(2023, 2, 1), savedTask.getDueDate());
    }

    @Test
    public void testDeleteTaskById() {
        TaskEntity task1 = new TaskEntity();
        task1.setCompleted(false);
        task1.setTitle("sample 1");
        task1.setDescription("sample description 1");
        task1.setDueDate(LocalDate.of(2023, 2, 1));

        var note = new NoteEntity();
        note.setBody("sample note 1");
        note.setTaskEntity(task1);

        var savedTask = taskRepository.save(task1);
        var savedNote = noteRepository.save(note);

        noteRepository.deleteAllByTaskEntity(savedTask);
        taskRepository.delete(savedTask);

        assertThat(taskRepository.findById(savedTask.getId())).isEmpty();
        assertThat(noteRepository.findById(savedNote.getId())).isEmpty();
    }
}
