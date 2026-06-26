package com.example.notetracker.notes.service;

import com.example.notetracker.notes.domain.Note;
import com.example.notetracker.notes.dto.CreateNoteRequest;
import com.example.notetracker.notes.dto.NoteResponse;
import com.example.notetracker.notes.repository.NoteRepository;
import com.example.notetracker.user.domain.UserEntity;
import com.example.notetracker.user.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserEntityRepository userEntityRepository;

    @Override
    @Transactional
    public NoteResponse createNote(String userId, CreateNoteRequest request) {
        UserEntity user = userEntityRepository.findById(userId)
                .orElseGet(() -> userEntityRepository.save(new UserEntity(userId)));

        Note note = new Note(
                request.title(),
                request.rawContent(),
                request.category(),
                request.tags(),
                user
        );

        return toResponse(noteRepository.save(note));
    }

    @Override
    public NoteResponse getNoteById(UUID id) {
        return noteRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    @Override
    public List<NoteResponse> getNotesByUserId(String userId) {
        return noteRepository.findByUser_IdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private NoteResponse toResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getRawContent(),
                note.getProcessedContent(),
                note.getCategory(),
                List.copyOf(note.getTags()),
                note.getCreatedAt(),
                note.getUpdatedAt(),
                note.getUser().getId()
        );
    }
}
