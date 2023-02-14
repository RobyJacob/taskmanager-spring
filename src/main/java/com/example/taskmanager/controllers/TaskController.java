package com.example.taskmanager.controllers;

import com.example.taskmanager.dtos.NewTaskRequest;
import com.example.taskmanager.dtos.ErrorResponse;
import com.example.taskmanager.dtos.NoteResponse;
import com.example.taskmanager.dtos.TaskResponse;
import com.example.taskmanager.entities.TaskEntity;
import com.example.taskmanager.services.NoteService;
import com.example.taskmanager.services.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    private final NoteService noteService;

    public TaskController(TaskService taskService, NoteService noteService) {
        this.taskService = taskService;
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<TaskEntity>> getTasks(@RequestParam(name = "completed") Optional<Boolean> completed,
                                                     @RequestParam(name = "dueDate") Optional<String> dueDate) {
        return completed.map(aBoolean -> ResponseEntity.ok(taskService.getTasksByStatus(aBoolean)))
                .orElseGet(() -> dueDate.map(date -> ResponseEntity.ok(taskService.getTasksByDueDate(date)))
                .orElseGet(() -> ResponseEntity.ok(taskService.getTasks())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Integer taskId) {
        TaskEntity reqTask = taskService.getTaskById(taskId);

        var notes = noteService.getNotesByTaskId(taskId);
        var mapper = new ModelMapper();

        var taskResponse = mapper.map(reqTask, TaskResponse.class);

        List<NoteResponse> noteResponses = new ArrayList<>();
        notes.forEach(note -> noteResponses.add(mapper.map(note, NoteResponse.class)));

        taskResponse.setNotes(noteResponses);

        return ResponseEntity.ok(taskResponse);
    }

    @PostMapping
    public ResponseEntity<TaskEntity> addTask(@RequestBody NewTaskRequest taskDto) {
        TaskEntity addedTask = taskService.addTask(taskDto);

        return ResponseEntity.created(URI.create("/task/" + addedTask.getId())).body(addedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Integer taskId) {
        noteService.deleteAllByTaskId(taskId);
        taskService.deleteTask(taskId);

        return ResponseEntity.accepted().body("Successfully deleted");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer taskId,
                                                 @RequestBody NewTaskRequest newTask) {
        TaskEntity updatedTask = taskService.updateTask(taskId, newTask);

        return ResponseEntity.accepted().body(updatedTask);
    }

    @ExceptionHandler({
            TaskService.TaskNotFoundException.class,
            RuntimeException.class
    })
    ResponseEntity<ErrorResponse> errorHandler(Exception ex) {
        if (ex instanceof RuntimeException)
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage()));
    }
}
