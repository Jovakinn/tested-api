package com.example.testingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.testingapp.dao.StudentRepository;
import com.example.testingapp.entity.Student;
import com.example.testingapp.entity.enums.Gender;
import com.example.testingapp.exceptions.BadRequestException;
import com.example.testingapp.exceptions.StudentNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StudentService.class})
@ExtendWith(SpringExtension.class)
class StudentServiceTest {
    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    /**
     * Method under test: {@link StudentService#getAllStudents()}
     */
    @Test
    void testGetAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        when(studentRepository.findAll()).thenReturn(studentList);
        List<Student> actualAllStudents = studentService.getAllStudents();
        assertSame(studentList, actualAllStudents);
        assertTrue(actualAllStudents.isEmpty());
        verify(studentRepository).findAll();
    }

    /**
     * Method under test: {@link StudentService#getAllStudents()}
     */
    @Test
    void testGetAllStudents2() {
        when(studentRepository.findAll()).thenThrow(new BadRequestException("An error occurred"));
        assertThrows(BadRequestException.class, () -> studentService.getAllStudents());
        verify(studentRepository).findAll();
    }

    /**
     * Method under test: {@link StudentService#addStudent(Student)}
     */
    @Test
    void testAddStudent() {
        Student student = new Student();
        student.setEmail("jane.doe@example.org");
        student.setGender(Gender.MALE);
        student.setId(123L);
        student.setName("Name");
        when(studentRepository.selectExistsEmail((String) any())).thenReturn(true);
        when(studentRepository.save((Student) any())).thenReturn(student);

        Student student1 = new Student();
        student1.setEmail("jane.doe@example.org");
        student1.setGender(Gender.MALE);
        student1.setId(123L);
        student1.setName("Name");
        assertThrows(BadRequestException.class, () -> studentService.addStudent(student1));
        verify(studentRepository).selectExistsEmail((String) any());
    }

    /**
     * Method under test: {@link StudentService#addStudent(Student)}
     */
    @Test
    void testAddStudent2() {
        Student student = new Student();
        student.setEmail("jane.doe@example.org");
        student.setGender(Gender.MALE);
        student.setId(123L);
        student.setName("Name");
        when(studentRepository.selectExistsEmail((String) any())).thenReturn(false);
        when(studentRepository.save((Student) any())).thenReturn(student);

        Student student1 = new Student();
        student1.setEmail("jane.doe@example.org");
        student1.setGender(Gender.MALE);
        student1.setId(123L);
        student1.setName("Name");
        studentService.addStudent(student1);
        verify(studentRepository).selectExistsEmail((String) any());
        verify(studentRepository).save((Student) any());
        assertEquals("jane.doe@example.org", student1.getEmail());
        assertEquals("Name", student1.getName());
        assertEquals(123L, student1.getId().longValue());
        assertEquals(Gender.MALE, student1.getGender());
    }

    /**
     * Method under test: {@link StudentService#addStudent(Student)}
     */
    @Test
    void testAddStudent3() {
        when(studentRepository.selectExistsEmail((String) any()))
                .thenThrow(new StudentNotFoundException("An error occurred"));
        when(studentRepository.save((Student) any())).thenThrow(new StudentNotFoundException("An error occurred"));

        Student student = new Student();
        student.setEmail("jane.doe@example.org");
        student.setGender(Gender.MALE);
        student.setId(123L);
        student.setName("Name");
        assertThrows(StudentNotFoundException.class, () -> studentService.addStudent(student));
        verify(studentRepository).selectExistsEmail((String) any());
    }

    /**
     * Method under test: {@link StudentService#deleteStudent(Long)}
     */
    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById((Long) any());
        when(studentRepository.existsById((Long) any())).thenReturn(true);
        studentService.deleteStudent(123L);
        verify(studentRepository).existsById((Long) any());
        verify(studentRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link StudentService#deleteStudent(Long)}
     */
    @Test
    void testDeleteStudent2() {
        doThrow(new BadRequestException("An error occurred")).when(studentRepository).deleteById((Long) any());
        when(studentRepository.existsById((Long) any())).thenReturn(true);
        assertThrows(BadRequestException.class, () -> studentService.deleteStudent(123L));
        verify(studentRepository).existsById((Long) any());
        verify(studentRepository).deleteById((Long) any());
    }

    /**
     * Method under test: {@link StudentService#deleteStudent(Long)}
     */
    @Test
    void testDeleteStudent3() {
        doNothing().when(studentRepository).deleteById((Long) any());
        when(studentRepository.existsById((Long) any())).thenReturn(false);
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(123L));
        verify(studentRepository).existsById((Long) any());
    }
}

