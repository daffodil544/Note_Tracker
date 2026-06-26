package com.example.notetracker.notes.controller;

import com.example.notetracker.authentication.Sender;
import com.example.notetracker.notes.dto.CreateNoteRequest;
import com.example.notetracker.notes.dto.NoteResponse;
import com.example.notetracker.notes.service.NoteNotFoundException;
import com.example.notetracker.notes.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NotesController {

    private final NoteService noteService;

    @PostMapping
    public NoteResponse createNote(@Valid @RequestBody CreateNoteRequest request) {
        return noteService.createNote(currentUserId(), request);
    }

    @GetMapping("/{id}")
    public NoteResponse getNote(@PathVariable UUID id) {
        return noteService.getNoteById(id);
    }

    @GetMapping
    public List<NoteResponse> getMyNotes() {
        return noteService.getNotesByUserId(currentUserId());
    }

    @ExceptionHandler(NoteNotFoundException.class)
    ResponseEntity<Void> handleNotFound() {
        return ResponseEntity.notFound().build();
    }

    private static String currentUserId() {
        Sender sender = Sender.getCurrent();
        if (sender == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }
        return sender.userId().getFunctionalId();
    }
}
