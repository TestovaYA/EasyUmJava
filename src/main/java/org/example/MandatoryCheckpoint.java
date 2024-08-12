package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Обязательный контрольный пункт.
 */
class MandatoryCheckpoint implements Checkpoint {
    private final String name;
    private final double latitude;
    private final double longitude;
    private final List<Car> parkedSedans = new ArrayList<>();
    private final List<Car> parkedTrucks = new ArrayList<>();

    public MandatoryCheckpoint(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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
        System.out.println("Mandatory checkpoint: " + name +
                " (latitude : " + latitude + ", longitude: " + longitude + ")");
        displayParkedCars();
    }
}
