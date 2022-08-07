package com.example.testingapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoteNotFound extends RuntimeException {
    public NoteNotFound(String message) {
        super(message);
    }
}
