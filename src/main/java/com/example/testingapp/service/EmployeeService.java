package com.example.testingapp.service;

import com.example.testingapp.dao.EmployeeRepository;
import com.example.testingapp.entity.Employee;
import com.example.testingapp.exceptions.EmployeeNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee addEmployee(Employee employee) {
        return employeeRepository.findByEmail(employee.getEmail())
                .orElseThrow(() -> new EmployeeNotFound("Employee not found"));
    }

    @Transactional
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFound("Employee not found"));
    }
}
