package org.example;

public class Main {
    public static void main(String[] args) {
        Track track = Track.getInstance();
        CheckpointFactory checkpointFactory = new ConcreteCheckpointFactory();

        Checkpoint cp1 = checkpointFactory.createCheckpoint("CP1", 45.0, 30.0, null);
        Checkpoint cp2 = checkpointFactory.createCheckpoint("CP2", 46.0, 31.0, 2.5);

        track.addCheckpoint(cp1);
        track.addCheckpoint(cp2);

        Car sedan1 = new Sedan.Builder("A123BC")
                .setColor("Red")
                .setPower(200)
                .setFuelCapacity(60)
                .build();

        Car sedan2 = new Sedan.Builder("A124BC")
                .setColor("Pink")
                .setPower(220)
                .setFuelCapacity(50)
                .build();

        Car truck1 = new Truck.Builder("B456CD")
                .setColor("Blue")
                .setPower(400)
                .setFuelCapacity(90)
                .build();

        Car truck2 = new Truck.Builder("B457CD")
                .setColor("Green")
                .setPower(450)
                .setFuelCapacity(100)
                .build();

        track.addCar(sedan1);
        track.addCar(sedan2);
        track.addCar(truck1);
        track.addCar(truck2);

        cp1.parkCar(sedan1);
        cp2.parkCar(truck1);

        for (Checkpoint checkpoint : track.getCheckpoints()) {
            checkpoint.displayInfo();
        }

        track.getCarPool().displayAvailableCars();

        Car borrowedCar = track.getCarPool().borrowCar();
        if (borrowedCar != null) {
            System.out.println("Borrowed: " + borrowedCar);
        } else {
            System.out.println("No available cars.");
        }

        track.getCarPool().displayAvailableCars();

        if (borrowedCar != null) {
            track.getCarPool().returnCar(borrowedCar);
            System.out.println("Returned: " + borrowedCar);
        }

        track.getCarPool().displayAvailableCars();
    }
}