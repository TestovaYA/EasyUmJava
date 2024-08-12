package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Необязательный контрольный пункт.
 */
class OptionalCheckpoint implements Checkpoint {
    private final String name;
    private final double latitude;
    private final double longitude;
    private final double penalty;
    private final List<Car> parkedSedans = new ArrayList<>();
    private final List<Car> parkedTrucks = new ArrayList<>();

    public OptionalCheckpoint(String name, double latitude, double longitude, double penalty) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.penalty = penalty;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    public double getPenalty() {
        return penalty;
    }

    @Override
    public void parkCar(Car car) {
        if (car.getCarType() == CarType.SEDAN) {
            parkedSedans.add(car);
        } else if (car.getCarType() == CarType.TRUCK) {
            parkedTrucks.add(car);
        }
    }

    @Override
    public void displayParkedCars() {
        System.out.println("Parked passenger cars on " + name + ":");
        for (Car car : parkedSedans) {
            System.out.println(car);
        }
        System.out.println("Parked trucks on " + name + ":");
        for (Car car : parkedTrucks) {
            System.out.println(car);
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Optional checkpoint: " + name +
                " (latitude: " + latitude + ", longitude: " + longitude +
                ", Penalty for missing: " + penalty + " hour(s))");
        displayParkedCars();
    }
}