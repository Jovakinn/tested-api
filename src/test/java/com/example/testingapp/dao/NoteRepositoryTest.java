package com.example.testingapp.dao;

import com.example.testingapp.entity.Note;
import com.example.testingapp.entity.Person;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {NoteRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.testingapp.entity"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @MockBean
    private PersonRepository personRepository;

    /**
     * Method under test: {@link NoteRepository#findByBody(String)}
     */
    @Test
    void testFindByBody() {
        //given
        Note note = new Note();
        note.setBody("Some note");

        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jack");
        person.setLastName("Doe");
        person.setNotes(List.of(note));
        personRepository.save(person);

        //when
        Optional<Note> founded = noteRepository.findByBody(note.getBody());

        //then
        founded.ifPresent(value -> assertThat(value.getId()).isEqualTo(note.getId()));
    }
}

