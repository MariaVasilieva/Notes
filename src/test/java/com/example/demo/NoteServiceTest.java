package com.example.demo;

import com.example.demo.note.NoteDto;

import com.example.demo.note.NoteRepository;
import com.example.demo.note.exception.NoteNotFoundExceptions;
import com.example.demo.note.model.Note;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.note.service.NoteService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void testGetAllNotesForUser() {

        Long userId = 1L;
        List<Note> notes = List.of(Note.builder().id(1L).title("Note 1").content("Content 1").build(),
                Note.builder().id(2L).title("Note 2").content("Content 2").build());
        when(noteRepository.findByUserId(userId)).thenReturn(notes);


        List<NoteDto> noteDtos = noteService.getAllNotesForUser(userId);


        assertEquals(2, noteDtos.size());
        assertEquals("Note 1", noteDtos.get(0).getTitle());
        assertEquals("Content 1", noteDtos.get(0).getContent());
        assertEquals("Note 2", noteDtos.get(1).getTitle());
        assertEquals("Content 2", noteDtos.get(1).getContent());
    }

    @Test
    void testCreateNote() {

        Long userId = 1L;
        NoteDto noteDto = NoteDto.builder().title("New Note").content("New Content").build();
        User user = User.builder().id(userId).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        NoteDto createdNoteDto = noteService.createNote(userId, noteDto);


        assertEquals(noteDto.getTitle(), createdNoteDto.getTitle());
        assertEquals(noteDto.getContent(), createdNoteDto.getContent());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void testDeleteNoteById() {

        long noteId = 1L;


        noteService.deleteNoteById(noteId);


        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    void testUpdateNote() {

        long noteId = 1L;
        NoteDto noteDto = NoteDto.builder().id(noteId).title("Updated Note").content("Updated Content").build();
        Note existingNote = Note.builder().id(noteId).title("Old Note").content("Old Content").build();
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));


        noteService.update(noteDto);


        verify(noteRepository, times(1)).save(existingNote);
        assertEquals(noteDto.getTitle(), existingNote.getTitle());
        assertEquals(noteDto.getContent(), existingNote.getContent());
    }

    @Test
    void testGetNoteById() {

        long noteId = 1L;
        Note note = Note.builder().id(noteId).title("Note").content("Content").build();
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));


        NoteDto noteDto = noteService.getNoteById(noteId);


        assertEquals(note.getTitle(), noteDto.getTitle());
        assertEquals(note.getContent(), noteDto.getContent());
    }

    @Test
    void testGetNoteByIdNotFound() {

        long noteId = 1L;
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());


        assertThrows(NoteNotFoundExceptions.class, () -> noteService.getNoteById(noteId));
    }
}