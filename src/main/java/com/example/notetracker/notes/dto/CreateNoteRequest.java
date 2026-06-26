package com.example.notetracker.notes.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateNoteRequest(
        @NotBlank String title,
        @NotBlank String rawContent,
        @NotBlank String category,
        List<String> tags
) {
}
