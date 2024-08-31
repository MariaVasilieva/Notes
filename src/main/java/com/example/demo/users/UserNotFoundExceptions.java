package com.example.demo.users;

public class UserNotFoundExceptions extends RuntimeException{
    public UserNotFoundExceptions(String message){
        super(message);
    }
}
