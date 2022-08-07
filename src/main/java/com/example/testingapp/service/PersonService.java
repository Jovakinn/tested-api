package com.example.testingapp.service;

import com.example.testingapp.dao.PersonRepository;
import com.example.testingapp.entity.Person;
import com.example.testingapp.exceptions.PersonNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;

    @Transactional
    public Person findByEmail(String email) {
        log.info("finding person by email");
        return personRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFound("person not found"));
    }

    @Transactional
    public Person findByFirstNameAndLastName(String firstName, String lastName) {
        log.info("finding person by firstName and lastName");
        return personRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new PersonNotFound("person not found"));
    }
}
