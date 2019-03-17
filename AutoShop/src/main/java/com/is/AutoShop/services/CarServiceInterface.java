package com.is.AutoShop.services;

import com.is.AutoShop.model.Car;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CarServiceInterface {
    public List<Car> getAllCars();
    public void addNewCar(Car car);
    public void deleteCar(String carId);
    public Car findCar(String carId);

}
