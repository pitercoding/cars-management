package com.cars.backend.entity;

public class Car {

    private String name;
    private String brand;
    private String model;
    private int manufactureYear;

    public Car() {

    }

    public Car(String name, String brand, String model, int manufactureYear) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.manufactureYear = manufactureYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }
}
