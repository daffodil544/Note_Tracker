package com.example.notetracker.notes.service;

import com.example.notetracker.notes.dto.CreateNoteRequest;
import com.example.notetracker.notes.dto.NoteResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class NoteServiceTest {

    @Autowired
    NoteService noteService;

    @Test
    void createAndRetrieveNote() {
        var request = new CreateNoteRequest(
                "Meeting notes",
                "Discuss roadmap and priorities.",
                "work",
                List.of("planning", "team")
        );

        var created = noteService.createNote("user-1", request);

        assertThat(created.id()).isNotNull();
        assertThat(created.title()).isEqualTo("Meeting notes");
        assertThat(created.rawContent()).isEqualTo("Discuss roadmap and priorities.");
        assertThat(created.processedContent()).isNull();
        assertThat(created.category()).isEqualTo("work");
        assertThat(created.tags()).containsExactly("planning", "team");
        assertThat(created.userId()).isEqualTo("user-1");
        assertThat(created.createdAt()).isNotNull();
        assertThat(created.updatedAt()).isNotNull();

        var fetched = noteService.getNoteById(created.id());
        assertThat(fetched).isEqualTo(created);
    }

    @Test
    void listNotesByUser() {
        noteService.createNote("user-1", new CreateNoteRequest("First", "Alpha", "personal", List.of()));
        noteService.createNote("user-1", new CreateNoteRequest("Second", "Beta", "personal", List.of()));
        noteService.createNote("user-2", new CreateNoteRequest("Other", "Gamma", "personal", List.of()));

        var notes = noteService.getNotesByUserId("user-1");

        assertThat(notes).hasSize(2);
        assertThat(notes).extracting(NoteResponse::title).containsExactly("Second", "First");
    }

    @Test
    void getMissingNoteThrows() {
        assertThatThrownBy(() -> noteService.getNoteById(UUID.randomUUID()))
                .isInstanceOf(NoteNotFoundException.class);
    }
}
