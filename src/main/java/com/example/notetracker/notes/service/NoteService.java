package com.example.notetracker.notes.service;

import com.example.notetracker.notes.dto.CreateNoteRequest;
import com.example.notetracker.notes.dto.NoteResponse;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    NoteResponse createNote(String userId, CreateNoteRequest request);

    NoteResponse getNoteById(UUID id);

    List<NoteResponse> getNotesByUserId(String userId);
}
