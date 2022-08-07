package com.example.testingapp.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.testingapp.dao.PersonRepository;
import com.example.testingapp.entity.Person;
import com.example.testingapp.exceptions.PersonNotFound;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PersonService.class})
@ExtendWith(SpringExtension.class)
class PersonServiceTest {
    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    /**
     * Method under test: {@link PersonService#findByEmail(String)}
     */
    @Test
    void testFindByEmail() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(123L);
        person.setLastName("Doe");
        person.setNotes(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertSame(person, personService.findByEmail("jane.doe@example.org"));
        verify(personRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link PersonService#findByEmail(String)}
     */
    @Test
    void testFindByEmail2() {
        when(personRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        assertThrows(PersonNotFound.class, () -> personService.findByEmail("jane.doe@example.org"));
        verify(personRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link PersonService#findByEmail(String)}
     */
    @Test
    void testFindByEmail3() {
        when(personRepository.findByEmail((String) any())).thenThrow(new PersonNotFound("Not all who wander are lost"));
        assertThrows(PersonNotFound.class, () -> personService.findByEmail("jane.doe@example.org"));
        verify(personRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link PersonService#findByFirstNameAndLastName(String, String)}
     */
    @Test
    void testFindByFirstNameAndLastName() {
        Person person = new Person();
        person.setEmail("jane.doe@example.org");
        person.setFirstName("Jane");
        person.setId(123L);
        person.setLastName("Doe");
        person.setNotes(new ArrayList<>());
        Optional<Person> ofResult = Optional.of(person);
        when(personRepository.findByFirstNameAndLastName((String) any(), (String) any())).thenReturn(ofResult);
        assertSame(person, personService.findByFirstNameAndLastName("Jane", "Doe"));
        verify(personRepository).findByFirstNameAndLastName((String) any(), (String) any());
    }

    /**
     * Method under test: {@link PersonService#findByFirstNameAndLastName(String, String)}
     */
    @Test
    void testFindByFirstNameAndLastName2() {
        when(personRepository.findByFirstNameAndLastName((String) any(), (String) any())).thenReturn(Optional.empty());
        assertThrows(PersonNotFound.class, () -> personService.findByFirstNameAndLastName("Jane", "Doe"));
        verify(personRepository).findByFirstNameAndLastName((String) any(), (String) any());
    }

    /**
     * Method under test: {@link PersonService#findByFirstNameAndLastName(String, String)}
     */
    @Test
    void testFindByFirstNameAndLastName3() {
        when(personRepository.findByFirstNameAndLastName((String) any(), (String) any()))
                .thenThrow(new PersonNotFound("Not all who wander are lost"));
        assertThrows(PersonNotFound.class, () -> personService.findByFirstNameAndLastName("Jane", "Doe"));
        verify(personRepository).findByFirstNameAndLastName((String) any(), (String) any());
    }
}

