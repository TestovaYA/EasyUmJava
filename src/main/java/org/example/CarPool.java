package org.example;

import java.util.ArrayList;
import java.util.List;

public class CarPool {
    private List<Car> availableCars = new ArrayList<>();
    private List<Car> usedCars = new ArrayList<>();

    public void addCar(Car car) {
        availableCars.add(car);
    }

    public Car borrowCar() {
        if (availableCars.isEmpty()) {
            return null;
        }
        Car car = availableCars.remove(availableCars.size() - 1);
        usedCars.add(car);
        return car;
    }

    public void returnCar(Car car) {
        usedCars.remove(car);
        availableCars.add(car);
    }

    public void displayAvailableCars() {
        System.out.println("Available Cars:");
        for (Car car : availableCars) {
            System.out.println(car);
        }
    }
}
