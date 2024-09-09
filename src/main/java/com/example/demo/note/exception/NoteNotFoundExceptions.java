package com.example.demo.note.exception;

public class NoteNotFoundExceptions extends RuntimeException{
    public NoteNotFoundExceptions(String message){
        super(message);
    }
}
