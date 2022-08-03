package com.example.testingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.testingapp.dao.EmployeeRepository;
import com.example.testingapp.entity.Employee;
import com.example.testingapp.exceptions.EmployeeNotFound;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmployeeService.class})
@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    /**
     * Method under test: {@link EmployeeService#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> actualAllEmployees = employeeService.getAllEmployees();
        assertSame(employeeList, actualAllEmployees);
        assertTrue(actualAllEmployees.isEmpty());
        verify(employeeRepository).findAll();
    }

    /**
     * Method under test: {@link EmployeeService#getAllEmployees()}
     */
    @Test
    void testGetAllEmployees2() {
        when(employeeRepository.findAll()).thenThrow(new EmployeeNotFound("Not all who wander are lost"));
        assertThrows(EmployeeNotFound.class, () -> employeeService.getAllEmployees());
        verify(employeeRepository).findAll();
    }

    /**
     * Method under test: {@link EmployeeService#addEmployee(Employee)}
     */
    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findByEmail((String) any())).thenReturn(ofResult);

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setFirstName("Jane");
        employee1.setId(123L);
        employee1.setLastName("Doe");
        employeeService.addEmployee(employee1);
        verify(employeeRepository).findByEmail((String) any());
        assertEquals("jane.doe@example.org", employee1.getEmail());
        assertEquals("Doe", employee1.getLastName());
        assertEquals(123L, employee1.getId());
        assertEquals("Jane", employee1.getFirstName());
    }

    /**
     * Method under test: {@link EmployeeService#addEmployee(Employee)}
     */
    @Test
    void testAddEmployee2() {
        when(employeeRepository.findByEmail((String) any())).thenReturn(Optional.empty());

        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        assertThrows(EmployeeNotFound.class, () -> employeeService.addEmployee(employee));
        verify(employeeRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link EmployeeService#addEmployee(Employee)}
     */
    @Test
    void testAddEmployee3() {
        when(employeeRepository.findByEmail((String) any()))
                .thenThrow(new EmployeeNotFound("Not all who wander are lost"));

        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        assertThrows(EmployeeNotFound.class, () -> employeeService.addEmployee(employee));
        verify(employeeRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link EmployeeService#getEmployeeByEmail(String)}
     */
    @Test
    void testGetEmployeeByEmail() {
        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setFirstName("Jane");
        employee.setId(123L);
        employee.setLastName("Doe");
        Optional<Employee> ofResult = Optional.of(employee);
        when(employeeRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertSame(employee, employeeService.getEmployeeByEmail("jane.doe@example.org"));
        verify(employeeRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link EmployeeService#getEmployeeByEmail(String)}
     */
    @Test
    void testGetEmployeeByEmail2() {
        when(employeeRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFound.class, () -> employeeService.getEmployeeByEmail("jane.doe@example.org"));
        verify(employeeRepository).findByEmail((String) any());
    }

    /**
     * Method under test: {@link EmployeeService#getEmployeeByEmail(String)}
     */
    @Test
    void testGetEmployeeByEmail3() {
        when(employeeRepository.findByEmail((String) any()))
                .thenThrow(new EmployeeNotFound("Not all who wander are lost"));
        assertThrows(EmployeeNotFound.class, () -> employeeService.getEmployeeByEmail("jane.doe@example.org"));
        verify(employeeRepository).findByEmail((String) any());
    }
}

