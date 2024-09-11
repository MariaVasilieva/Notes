package com.example.demo;


import com.example.demo.auth.config.Constant;
import com.example.demo.note.model.AccessType;
import com.example.demo.note.model.Note;
import com.example.demo.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("test");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testNoteCreation() {
        // Given
        User user = new User();
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Content");
        note.setUser(user);
        note.setAccessType(AccessType.PRIVATE);
        note.setColor(Constant.WHITE_COLOR);

        // When
        entityManager.getTransaction().begin();
        entityManager.persist(note);
        entityManager.getTransaction().commit();

        // Then
        assertNotNull(note.getId());
        assertEquals("Test Note", note.getTitle());
        assertEquals("Content", note.getContent());
        assertEquals(user, note.getUser());
        assertEquals(AccessType.PRIVATE, note.getAccessType());
        assertEquals(Constant.WHITE_COLOR, note.getColor());
    }

    @Test
    void testNoteUpdate() {
        // Given
        User user = new User();
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Content");
        note.setUser(user);
        note.setAccessType(AccessType.PRIVATE);
        note.setColor(Constant.WHITE_COLOR);
        entityManager.getTransaction().begin();
        entityManager.persist(note);
        entityManager.getTransaction().commit();

        // When
        note.setTitle("Updated Title");
        note.setContent("Updated Content");
        note.setAccessType(AccessType.PUBLIC);
        note.setColor(null);
        entityManager.getTransaction().begin();
        entityManager.merge(note);
        entityManager.getTransaction().commit();

        // Then
        assertEquals("Updated Title", note.getTitle());
        assertEquals("Updated Content", note.getContent());
        assertEquals(AccessType.PUBLIC, note.getAccessType());
        assertNull(note.getColor());
    }

    @Test
    void testNoteDeletion() {
        // Given
        User user = new User();
        Note note = new Note();
        note.setTitle("Test Note");
        note.setContent("Content");
        note.setUser(user);
        note.setAccessType(AccessType.PRIVATE);
        note.setColor(Constant.WHITE_COLOR);
        entityManager.getTransaction().begin();
        entityManager.persist(note);
        entityManager.getTransaction().commit();

        // When
        entityManager.getTransaction().begin();
        entityManager.remove(note);
        entityManager.getTransaction().commit();

        // Then
        assertNull(entityManager.find(Note.class, note.getId()));
    }
}