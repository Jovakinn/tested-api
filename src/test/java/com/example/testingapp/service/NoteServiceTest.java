package com.example.testingapp.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.testingapp.dao.NoteRepository;
import com.example.testingapp.entity.Note;
import com.example.testingapp.entity.Person;
import com.example.testingapp.exceptions.NoteNotFound;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NoteService.class})
@ExtendWith(SpringExtension.class)
class NoteServiceTest {
    @MockBean
    private NoteRepository noteRepository;

    @Autowired
    private NoteService noteService;

    /**
     * Method under test: {@link NoteService#getByBody(String)}
     */
    @Test
    void testGetByBody() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(123L);
        person.setLastName("Doe");
        person.setNotes(new ArrayList<>());

        Note note = new Note();
        note.setBody("Not all who wander are lost");
        note.setId(123L);
        note.setPerson(person);
        Optional<Note> ofResult = Optional.of(note);
        when(noteRepository.findByBody((String) any())).thenReturn(ofResult);
        assertSame(note, noteService.getByBody("Not all who wander are lost"));
        verify(noteRepository).findByBody((String) any());
    }

    /**
     * Method under test: {@link NoteService#getByBody(String)}
     */
    @Test
    void testGetByBody2() {
        when(noteRepository.findByBody((String) any())).thenReturn(Optional.empty());
        assertThrows(NoteNotFound.class, () -> noteService.getByBody("Not all who wander are lost"));
        verify(noteRepository).findByBody((String) any());
    }

    /**
     * Method under test: {@link NoteService#getByBody(String)}
     */
    @Test
    void testGetByBody3() {
        when(noteRepository.findByBody((String) any())).thenThrow(new NoteNotFound("Not all who wander are lost"));
        assertThrows(NoteNotFound.class, () -> noteService.getByBody("Not all who wander are lost"));
        verify(noteRepository).findByBody((String) any());
    }
}

