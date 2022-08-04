package com.example.testingapp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "notes")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "body", nullable = false)
    private String body;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")
    private Person person;

    public Note(String body) {
        this.body = body;
    }
}