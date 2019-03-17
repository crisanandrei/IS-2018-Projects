package com.is.AutoShop.services;

import com.is.AutoShop.model.Car;
import com.is.AutoShop.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarService implements CarServiceInterface {
    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> set = new ArrayList<>();
        carRepository.findAll().forEach(car -> {
            if (!car.isOrdered()){
                set.add(car);
        }
        });
        return set;
    }

    @Override
    public void addNewCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public void deleteCar(String carId) {
        carRepository.deleteByCarID(carId);
    }

    @Override
    public Car findCar(String carId) {

        if (carRepository.findById(carId).isPresent()){
            return carRepository.findById(carId).get();
        }
        return null;
    }
}
