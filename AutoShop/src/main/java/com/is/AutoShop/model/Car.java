package com.is.AutoShop.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Car {
    @Id
    private String carID;

    public String make;
    public String model;
    public FuelType fuelType;
    public String fabYear;
    public Integer price;
    public String description;
    public String carImage;
    public boolean ordered = false;

    public Car(String make, String model, FuelType fuelType, String fabYear, Integer price, String description, String carImage) {
        this.make = make;
        this.model = model;
        this.fuelType = fuelType;
        this.fabYear = fabYear;
        this.price = price;
        this.description = description;
        this.carImage = carImage;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getFabYear() {
        return fabYear;
    }

    public void setFabYear(String fabYear) {
        this.fabYear = fabYear;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCarImage() {
        return carImage;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }
}