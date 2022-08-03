package com.example.testingapp.dao;

import com.example.testingapp.entity.Employee;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;


@ContextConfiguration(classes = {EmployeeRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.testingapp.entity"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;



    @Test
    @Order(1)
    @Rollback(value = false)
    void saveEmployeeTest() {
        var employee = Employee.builder()
                .firstName("Max")
                .lastName("Khodakov")
                .email("max05012004@gmail.com")
                .build();
        employeeRepository.save(employee);
        assertThat(employee.getId()).isPositive();
    }

    @Test
    @Order(2)
    void getEmployeeTest() {
        var employee = employeeRepository.findById(1L).get();
        assertThat(employee.getId()).isPositive();
    }

    @Test
    @Order(3)
    void getListOfEmployeesTest() {
        var employee = employeeRepository.findAll();
        assertThat(employee.size()).isPositive();
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    void updateEmployeesTest() {
        var employee = employeeRepository.findById(1L).get();
        employee.setEmail("cringe@gmail.com");
        Employee saved = employeeRepository.save(employee);
        assertThat(saved.getEmail()).isEqualTo("cringe@gmail.com");
    }

    /**
     * Method under test: {@link EmployeeRepository#findByEmail(String)}
     */
    @Test
    @Order(5)
    void testFindByEmail() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setLastName("Doe");

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstName("Jane");
        employee1.setLastName("Doe");
        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        assertFalse(employeeRepository.findByEmail("foo").isPresent());
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    void deleteEmployeeTest() {
        Employee employee = employeeRepository.findById(1L).get();
        employeeRepository.delete(employee);
        Employee emp = null;
        Optional<Employee> byEmail = employeeRepository.findByEmail("cringe@gmail.com");
        if (byEmail.isPresent()) {
            emp = byEmail.get();
        }
        assertThat(emp).isNull();
    }
}