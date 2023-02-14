package com.example.taskmanager.services;

import com.example.taskmanager.dtos.NewTaskRequest;
import com.example.taskmanager.entities.TaskEntity;
import com.example.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskEntity> getTasks() {
        return taskRepository.findAll();
    }

    public TaskEntity getTaskById(Integer taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    public TaskEntity addTask(NewTaskRequest taskDto) {
        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDescription(taskDto.getDescription());
        taskEntity.setCompleted(Boolean.valueOf(taskDto.getCompleted()));
        taskEntity.setDueDate(LocalDate.parse(taskDto.getDueDate(), dateFormat));

        return taskRepository.save(taskEntity);
    }

    public void deleteTask(Integer taskId) {
        TaskEntity taskToDelete = getTaskById(taskId);

        taskRepository.delete(taskToDelete);
    }

    public TaskEntity updateTask(Integer taskId, NewTaskRequest newTask) {
        TaskEntity taskToUpdate = getTaskById(taskId);

        if (Objects.nonNull(newTask.getTitle()))
            taskToUpdate.setTitle(newTask.getTitle());
        if (Objects.nonNull(newTask.getDescription()))
            taskToUpdate.setDescription(newTask.getDescription());
        if (Objects.nonNull(newTask.getDueDate()))
            taskToUpdate.setDueDate(LocalDate.parse(newTask.getDueDate(), dateFormat));
        if (Objects.nonNull(newTask.getCompleted()))
            taskToUpdate.setCompleted(Boolean.valueOf(newTask.getCompleted()));

        taskRepository.save(taskToUpdate);

        return taskToUpdate;
    }

    public List<TaskEntity> getTasksByStatus(Boolean completed) {
        return taskRepository.findAllByCompleted(completed);
    }

    public List<TaskEntity> getTasksByDueDate(String date) {
        return taskRepository.findAllByDueDate(LocalDate.parse(date, dateFormat));
    }

    public static class TaskNotFoundException extends IllegalArgumentException {
        TaskNotFoundException(Integer id) {
            super("TaskEntity id " + id + " is not found");
        }
    }
}
