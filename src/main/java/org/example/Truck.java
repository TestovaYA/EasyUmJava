package org.example;

/**
 * Класс для грузовиков.
 */
public class Truck extends Car {
    public Truck(Builder builder) {
        super(builder);
    }
    @Override
    public CarType getCarType() {
        return CarType.TRUCK;
    }

    /**
     * Строитель для грузовиков.
     */
    public static class Builder extends Car.Builder<Builder> {
        public Builder(String carNumber) {
            super(carNumber);
        }

        @Override
        public Truck build() {
            return new Truck(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}