package com.example.testingapp.dao;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.testingapp.entity.Student;
import com.example.testingapp.entity.enums.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {StudentRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.testingapp.entity"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    /**
     * Method under test: {@link StudentRepository#selectExistsEmail(String)}
     */
    @Test
    void testSelectExistsEmail1() {
        Student student = new Student();
        student.setEmail("");
        student.setGender(Gender.MALE);
        student.setName("Name");

        Student student1 = new Student();
        student1.setEmail("jane.doe@example.org");
        student1.setGender(Gender.MALE);
        student1.setName("Name");
        studentRepository.save(student);
        studentRepository.save(student1);
        assertFalse(studentRepository.selectExistsEmail("foo"));
    }

    /**
     * Method under test: {@link StudentRepository#selectExistsEmail(String)}
     */
    @Test
    void testSelectExistsEmail2() {
        Student student = new Student();
        student.setEmail("");
        student.setGender(Gender.MALE);
        student.setName("Name");

        Student student1 = new Student();
        student1.setEmail("jane.doe@example.org");
        student1.setGender(Gender.MALE);
        student1.setName("Name");
        studentRepository.save(student);
        studentRepository.save(student1);
        assertTrue(studentRepository.selectExistsEmail(""));
    }
}

