package com.example.demo.note.controller;

import com.example.demo.note.model.Note;
import com.example.demo.user.UserService;
import com.example.demo.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    @GetMapping("/list")
    public String getNoteList(Model model) {
        List<Note> notes = noteService.findAll();
        model.addAttribute("notes", notes);
        return "note-list";

    }
}
