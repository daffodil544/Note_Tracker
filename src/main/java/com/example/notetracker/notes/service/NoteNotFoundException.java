package com.example.notetracker.notes.service;

import java.util.UUID;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(UUID id) {
        super("Note not found: " + id);
    }
}
