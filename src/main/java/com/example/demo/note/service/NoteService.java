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
import java.util.Optional;
import java.util.stream.Collectors;
import static java.text.MessageFormat.format;
//import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class NoteService {
    public static final int NOTE_TITLE_MIN_LENGTH = 5;
    public static final int NOTE_TITLE_MAX_LENGTH = 100;
    public static final int NOTE_CONTENT_MIN_LENGTH = 5;
    public static final int NOTE_CONTENT_MAX_LENGTH = 10000;
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
    public Optional<Note> getById(String id) {
        return noteRepository.findById(Long.valueOf(id));
    }

    public NoteDto getNoteById(long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundExceptions("Note with id " + id + " was not found"));
        return mapToDto(note);
    }
    private NoteDto mapToDto(Note note) {
        return NoteDto.builder()
                .id(Long.parseLong(note.getId()))
                .title(note.getTitle())
                .content(note.getContent())
                .accessType(note.getAccessType())
                .build();
    }
    private Note mapToEntity(NoteDto noteDto) {
        return Note.builder()
                .id(String.valueOf(noteDto.getId()))
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .accessType(noteDto.getAccessType())
                .build();
    }
    public void save(Note note) {

        validateNote(note);

        noteRepository.save(note);
    }
    private void validateNote(Note note) {
        if (note.getTitle().length() < NOTE_TITLE_MIN_LENGTH || note.getTitle().length() >= NOTE_TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException(format("Note title should be between {0} and {1} characters", NOTE_TITLE_MIN_LENGTH, NOTE_TITLE_MAX_LENGTH));
        }

        if (note.getContent().length() < NOTE_CONTENT_MIN_LENGTH || note.getContent().length() >= NOTE_CONTENT_MAX_LENGTH) {
            throw new IllegalArgumentException(format("Note content should be between {0} and {1}  characters", NOTE_CONTENT_MIN_LENGTH, NOTE_CONTENT_MAX_LENGTH));
        }

    }
}
