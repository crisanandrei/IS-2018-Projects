package com.is.AutoShop.services;

import com.is.AutoShop.model.Employee;
import com.is.AutoShop.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService implements EmployeServiceInterface{
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addNewEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeRepository.findAll().iterator().forEachRemaining(employeeList::add);
        return employeeList;
    }

    @Override
    public Employee findEmployee(String employeeId) {
        if(employeeRepository.findById(employeeId).isPresent()) {
            return employeeRepository.findById(employeeId).get();
        }
        return null;
    }
}
