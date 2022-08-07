package com.example.testingapp.service;

import com.example.testingapp.dao.NoteRepository;
import com.example.testingapp.entity.Note;
import com.example.testingapp.exceptions.NoteNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class NoteService {
    private final NoteRepository noteRepository;

    @Transactional
    public Note getByBody(String body) {
        log.info("getting note by body");
        return noteRepository.findByBody(body)
                .orElseThrow(() -> new NoteNotFound("note with body:"+body+ " not found"));
    }
}
