package com.example.testingapp.controller;

import com.example.testingapp.entity.Person;
import com.example.testingapp.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/persons")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/{email}")
    public ResponseEntity<Person> getPersonByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(personService.findByEmail(email));
    }

    @GetMapping
    public ResponseEntity<Person> getPersonByNameAndSurname(
            @RequestParam("first_name") String firstName,
            @RequestParam("last_name") String lastName) {
        return ResponseEntity.ok(personService.findByFirstNameAndLastName(firstName, lastName));
    }
}
