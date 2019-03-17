package com.is.AutoShop.services;

import com.is.AutoShop.model.Employee;

import java.util.List;

public interface EmployeServiceInterface {
    void addNewEmployee(Employee employee);
    void deleteEmployee(String employeeId);
    List<Employee> getAllEmployees();
    Employee findEmployee(String employeeId);
}
