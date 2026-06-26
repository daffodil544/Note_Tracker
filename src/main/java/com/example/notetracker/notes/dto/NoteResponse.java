package com.example.notetracker.notes.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record NoteResponse(
        UUID id,
        String title,
        String rawContent,
        String processedContent,
        String category,
        List<String> tags,
        Instant createdAt,
        Instant updatedAt,
        String userId
) {
}
