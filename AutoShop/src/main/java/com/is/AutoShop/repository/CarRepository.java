package com.is.AutoShop.repository;


import com.is.AutoShop.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface CarRepository extends MongoRepository<Car,String> {
    public Car findCarByCarID(String carId);
    public void deleteByCarID(String carId);

}
