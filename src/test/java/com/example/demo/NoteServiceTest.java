package com.example.demo;

import com.example.demo.note.NoteRepository;
import com.example.demo.note.model.Note;

import com.example.demo.note.service.NoteService;
import com.example.demo.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private User user;
    private Note note;

    @BeforeEach
    public void setup() {
        user = new User();
        note = new Note();
        note.setTitle("Test Note");
        note.setContent("Content");
        note.setUser(user);
    }

    @Test
    void testSaveNote() {
        // Given
        when(noteRepository.save(note)).thenReturn(note);

        // When
        noteService.save(note);

        // Then
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void testSaveNote_InvalidTitle() {
        // Given
        note.setTitle("Too short");

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> noteService.save(note));
    }

    @Test
    void testSaveNote_InvalidContent() {
        // Given
        note.setContent("Too short");

        // When and Then
        assertThrows(IllegalArgumentException.class, () -> noteService.save(note));
    }

    @Test
    void testGetById() {
        // Given
        when(noteRepository.findById("1")).thenReturn(Optional.of(note));

        // When
        Optional<Note> result = noteService.getById("1");

        // Then
        assertTrue(result.isPresent());
        assertEquals(note, result.get());
    }

    @Test
    void testGetById_NotFound() {
        // Given
        when(noteRepository.findById("1")).thenReturn(Optional.empty());

        // When
        Optional<Note> result = noteService.getById("1");

        // Then
        assertFalse(result.isPresent());
    }

    @Test
    void testFindByUser() {
        // Given
        List<Note> notes = List.of(note);
        when(noteRepository.findByUser(user)).thenReturn(notes);

        // When
        List<Note> result = noteService.findByUser(user);

        // Then
        assertEquals(notes, result);
    }

    @Test
    void testListNoteByContent() {
        // Given
        List<Note> notes = List.of(note);
        when(noteRepository.findbyContentList(1L, "%content%")).thenReturn(notes);

        // When
        List<Note> result = noteService.listNoteByContent(1L, "content");

        // Then
        assertEquals(notes, result);
    }

    @Test
    void testDeleteById() {
        // Given
        doNothing().when(noteRepository).deleteById("1");

        // When
        noteService.deleteById("1");

        // Then
        verify(noteRepository, times(1)).deleteById("1");
    }
}