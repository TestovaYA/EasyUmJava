package org.example;

/**
 * Интерфейс для контрольного пункта.
 */
public interface Checkpoint {
    String getName();
    double getLatitude();
    double getLongitude();

    /**
     * Паркует автомобиль на контрольном пункте.
     *
     * @param car автомобиль, который нужно припарковать
     */
    void parkCar(Car car);

    /**
     * Отображает припаркованные автомобили на контрольном пункте.
     */
    void displayParkedCars();

    /**
     * Отображает информацию о контрольном пункте.
     */
    void displayInfo();
}