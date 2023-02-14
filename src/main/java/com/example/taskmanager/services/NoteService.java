package com.example.taskmanager.services;

import com.example.taskmanager.entities.NoteEntity;
import com.example.taskmanager.repositories.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    private final TaskService taskService;

    public NoteService(NoteRepository noteRepository1, TaskService taskService) {
        this.noteRepository = noteRepository1;
        this.taskService = taskService;
    }

    public NoteEntity addNoteByTaskId(Integer taskId, NoteEntity note) {
        var task = taskService.getTaskById(taskId);

        note.setTaskEntity(task);

        return noteRepository.save(note);
    }

    public void deleteNote(Integer taskId, Integer noteId) {
        noteRepository.deleteByTaskEntityAndId(taskService.getTaskById(taskId), noteId);
    }

    public void deleteAllByTaskId(Integer taskId) {
        noteRepository.deleteAllByTaskEntity(taskService.getTaskById(taskId));
    }

    public List<NoteEntity> getNotesByTaskId(Integer taskId) {
        return noteRepository.findAllByTaskEntity(taskService.getTaskById(taskId));
    }
}
