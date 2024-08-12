package org.example;

/**
 * Класс для легковых автомобилей.
 */
public class Sedan extends Car {
    public Sedan(Builder builder) {
        super(builder);
    }
    @Override
    public CarType getCarType() {
        return CarType.SEDAN;
    }

    /**
     * Строитель для легковых автомобилей.
     */
    public static class Builder extends Car.Builder<Builder> {
        public Builder(String licensePlate) {
            super(licensePlate);
        }

        @Override
        public Sedan build() {
            return new Sedan(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}