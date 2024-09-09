package com.example.demo.note.service;

import com.example.demo.note.exception.NoteNotFoundExceptions;
import com.example.demo.user.User;
import com.example.demo.note.NoteDto;
import com.example.demo.note.model.Note;
import com.example.demo.note.NoteRepository;
import com.example.demo.user.UserNotFoundExceptions;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public List<NoteDto> getAllNotesForUser(Long userId) {
        return noteRepository.findByUserId(userId)
                .stream()
                .map(p->mapToDto(p))
                .collect(Collectors.toList());
    }


    public NoteDto createNote(long userId, NoteDto noteDto) {
        Note note = mapToEntity(noteDto);
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundExceptions("User with id "+userId+" was not found"));
        note.setUser(user);
        noteRepository.save(note);
        return mapToDto(note);
    }


    public void deleteNoteById(long id) {
        noteRepository.deleteById(id);
    }


    public void update(NoteDto noteDto) {
        Note existingNote = noteRepository.findById(noteDto.getId())
                .orElseThrow(()-> new NoteNotFoundExceptions("Note with id " + noteDto.getId() + " was not found"));
        existingNote.setTitle(noteDto.getTitle());
        existingNote.setContent(noteDto.getContent());
        existingNote.setAccessType(noteDto.getAccessType());
        noteRepository.save(existingNote);
    }


    public NoteDto getNoteById(long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundExceptions("Note with id " + id + " was not found"));
        return mapToDto(note);
    }
    private NoteDto mapToDto(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .accessType(note.getAccessType())
                .build();
    }
    private Note mapToEntity(NoteDto noteDto) {
        return Note.builder()
                .id(noteDto.getId())
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .accessType(noteDto.getAccessType())
                .build();
    }
}
