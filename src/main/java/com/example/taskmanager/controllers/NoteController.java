package com.example.taskmanager.controllers;

import com.example.taskmanager.dtos.NoteResponse;
import com.example.taskmanager.entities.NoteEntity;
import com.example.taskmanager.services.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks/{task_id}/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotesByTaskId(@PathVariable("task_id") Integer taskId) {
        var notes = noteService.getNotesByTaskId(taskId);

        List<NoteResponse> noteResponses = new ArrayList<>();
        var mapper = new ModelMapper();

        notes.forEach(note -> noteResponses.add(mapper.map(note, NoteResponse.class)));

        return ResponseEntity.ok(noteResponses);
    }

    @PostMapping
    public ResponseEntity<NoteEntity> addNoteByTaskId(@PathVariable("task_id") Integer taskId,
                                                      @RequestBody NoteEntity note) {
        return ResponseEntity.created(URI.create("/tasks/" + taskId + "/notes"))
                .body(noteService.addNoteByTaskId(taskId, note));
    }

    @DeleteMapping("/{note_id}")
    public ResponseEntity<String> deleteNote(@PathVariable("task_id") Integer taskId,
                                             @PathVariable("note_id") Integer noteId) {
        noteService.deleteNote(taskId, noteId);

        return ResponseEntity.accepted().body("Successfully deleted");
    }
}
