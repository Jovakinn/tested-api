package com.example.testingapp.dao;

import com.example.testingapp.entity.Note;
import com.example.testingapp.entity.Person;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;


@ContextConfiguration(classes = {PersonRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.testingapp.entity"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @MockBean
    private NoteRepository noteRepository;

    /**
     * Method under test: {@link PersonRepository#findByEmail(String)}
     */
    @Test
    void testFindByEmail() {
        //given
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setLastName("Doe");
        person.setNotes(new ArrayList<>());
        Note note = new Note(1L, "Note without note", person);

        noteRepository.save(note);
        person.addNote(note);
        personRepository.save(person);

        //when
        Person byEmail = personRepository.findByEmail(person.getEmail()).get();

        //then
        assertThat(byEmail.getEmail()).isEqualTo(person.getEmail());
    }

    /**
     * Method under test: {@link PersonRepository#findByFirstNameAndLastName(String, String)}
     */
    @Test
    void testFindByFirstNameAndLastName() {
        //given
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setLastName("Doe");
        person.setNotes(new ArrayList<>());
        Note note = new Note(1L, "Note without note", person);

        noteRepository.save(note);
        person.addNote(note);
        personRepository.save(person);

        //when
        Person founded = personRepository.findByFirstNameAndLastName
                (person.getFirstName(), person.getLastName()).get();

        //then
        assertThat(founded.getId()).isEqualTo(person.getId());
    }
}

