package com.example.testingapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.testingapp.entity.Note;
import com.example.testingapp.entity.Person;
import com.example.testingapp.service.NoteService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {NoteController.class})
@ExtendWith(SpringExtension.class)
class NoteControllerTest {
    @Autowired
    private NoteController noteController;

    @MockBean
    private NoteService noteService;

    /**
     * Method under test: {@link NoteController#getNoteByBody(String)}
     */
    @Test
    void testGetNoteByBody() throws Exception {
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
        when(noteService.getByBody((String) any())).thenReturn(note);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/notes").param("body", "foo");
        MockMvcBuilders.standaloneSetup(noteController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"body\":\"Not all who wander are lost\",\"person\":{\"id\":123,\"firstName\":\"Jane\",\"lastName\":\"Doe"
                                        + "\",\"email\":\"jane.doe@example.org\",\"notes\":[]}}"));
    }
}

