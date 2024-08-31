package com.example.demo.notes.service;

import com.example.demo.notes.NoteDto;

import java.util.List;

public interface NoteService {
    List<NoteDto> getAllNotesForUser(Long userId);
    NoteDto createNote(long userId, NoteDto note);
    void deleteNoteById(long id);
    void update(NoteDto note);
    NoteDto getNoteById(long id);
}
