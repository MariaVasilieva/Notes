package com.example.demo.notes.exceptions;

public class NoteNotFoundExceptions extends RuntimeException{
    public NoteNotFoundExceptions(String message){
        super(message);
    }
}
