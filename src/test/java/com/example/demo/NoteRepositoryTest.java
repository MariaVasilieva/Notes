package com.example.demo;

import com.example.demo.note.NoteRepository;
import com.example.demo.note.model.Note;
import com.example.demo.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

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
    void testFindByUser() {
        // Given
        noteRepository.save(note);

        // When
        List<Note> result = noteRepository.findByUser(user);

        // Then
        assertEquals(1, result.size());
        assertEquals(note, result.get(0));
    }

    @Test
    void testFindbyContentList() {
        // Given
        noteRepository.save(note);

        // When
        List<Note> result = noteRepository.findbyContentList(1L, "content");

        // Then
        assertEquals(1, result.size());
        assertEquals(note, result.get(0));
    }

    @Test
    void testFindbyContentList_NoResults() {
        // When
        List<Note> result = noteRepository.findbyContentList(1L, "non-existent");

        // Then
        assertEquals(0, result.size());
    }
}