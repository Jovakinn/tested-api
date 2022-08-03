package com.example.testingapp.service;

import com.example.testingapp.dao.EmployeeRepository;
import com.example.testingapp.entity.Employee;
import com.example.testingapp.exceptions.EmployeeNotFound;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void addEmployee(Employee employee) {
        employeeRepository.findByEmail(employee.getEmail())
                .orElseThrow(() -> new EmployeeNotFound("Employee not found"));
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFound("Employee not found"));
    }
}
