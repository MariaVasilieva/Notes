package com.example.demo.user;

public class UserNotFoundExceptions extends RuntimeException{
    public UserNotFoundExceptions(String message){
        super(message);
    }
}
