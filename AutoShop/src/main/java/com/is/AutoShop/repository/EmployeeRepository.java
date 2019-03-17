package com.is.AutoShop.repository;


import com.is.AutoShop.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeRepository extends MongoRepository<Employee,String> {

}
