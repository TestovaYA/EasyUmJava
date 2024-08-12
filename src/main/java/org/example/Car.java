package org.example;

/**
 * Абстрактный класс для автомобилей.
 */
public abstract class Car {
    protected String carNumber;
    protected String color;
    protected int power;
    protected double fuelCapacity;

    public Car(Builder<?> builder) {
        this.carNumber = builder.carNumber;
        this.color = builder.color;
        this.power = builder.power;
        this.fuelCapacity = builder.fuelCapacity;
    }
    public abstract CarType getCarType();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{carNumber='" + carNumber + '\'' +
                ", color='" + color + '\'' +
                ", power=" + power +
                ", fuelCapacity=" + fuelCapacity + '}';
    }

    /**
     * Строитель для автомобилей.
     *
     * @param <T> тип конкретного строителя
     */
    public static abstract class Builder<T extends Builder<T>> {
        private final String carNumber;
        protected String color;
        protected int power;
        protected double fuelCapacity;

        protected Builder(String carNumber) {
            this.carNumber = carNumber;
        }

        public T setColor(String color) {
            this.color = color;
            return self();
        }

        public T setPower(int power) {
            this.power = power;
            return self();
        }

        public T setFuelCapacity(double fuelCapacity) {
            this.fuelCapacity = fuelCapacity;
            return self();
        }

        protected abstract T self();

        public abstract Car build();
    }
}
