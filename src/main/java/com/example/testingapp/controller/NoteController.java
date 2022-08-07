package com.example.testingapp.controller;

import com.example.testingapp.entity.Note;
import com.example.testingapp.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notes")
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<Note> getNoteByBody(@RequestParam("body") String body) {
        return ResponseEntity.ok(noteService.getByBody(body));
    }
}
