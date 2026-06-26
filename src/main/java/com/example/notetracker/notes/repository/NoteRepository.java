package com.example.notetracker.notes.repository;

import com.example.notetracker.notes.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    List<Note> findByUser_IdOrderByCreatedAtDesc(String userId);
}
