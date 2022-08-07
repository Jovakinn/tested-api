package com.example.testingapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persons")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        note.setPerson(this);
        notes.add(note);
    }
}
