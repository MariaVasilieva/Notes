package com.example.demo.notes.service;

import com.example.demo.notes.exceptions.NoteNotFoundExceptions;
import com.example.demo.users.User;
import com.example.demo.notes.NoteDto;
import com.example.demo.notes.model.Note;
import com.example.demo.notes.NoteRepository;
import com.example.demo.users.UserNotFoundExceptions;
import com.example.demo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService{
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public List<NoteDto> getAllNotesForUser(Long userId) {
        return noteRepository.findByUserId(userId)
                .stream()
                .map(p->mapToDto(p))
                .collect(Collectors.toList());
    }

    @Override
    public NoteDto createNote(long userId, NoteDto noteDto) {
        Note note = mapToEntity(noteDto);
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundExceptions("User with id "+userId+" was not found"));
        note.setUser(user);
        noteRepository.save(note);
        return mapToDto(note);
    }

    @Override
    public void deleteNoteById(long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public void update(NoteDto noteDto) {
        Note existingNote = noteRepository.findById(noteDto.getId())
                .orElseThrow(()-> new NoteNotFoundExceptions("Note with id " + noteDto.getId() + " was not found"));
        existingNote.setTitle(noteDto.getTitle());
        existingNote.setContent(noteDto.getContent());
        existingNote.setAccessType(noteDto.getAccessType());
        noteRepository.save(existingNote);
    }

    @Override
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
