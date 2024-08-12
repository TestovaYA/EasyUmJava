package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для управления трассой
 */
public class Track {
    private static Track instance;
    private final List<Checkpoint> checkpoints = new ArrayList<>();
    private final CarPool carPool = new CarPool();

    private Track() {}

    public static Track getInstance() {
        if (instance == null) {
            instance = new Track();
        }
        return instance;
    }

    /**
     * Добавляет контрольный пункт в список КП трассы.
     *
     * @param checkpoint контрольный пункт, который нужно добавить
     */
    public void addCheckpoint(Checkpoint checkpoint) {
        checkpoints.add(checkpoint);
    }

    /**
     * Добавляет автомобиль в пул автомобилей трассы.
     *
     * @param car автомобиль, который нужно добавить
     */
    public void addCar(Car car) {
        carPool.addCar(car);
    }

    /**
     * Получение списка контрольных пунктов трассы.
     */
    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    /**
     * Получение пула машин.
     */
    public CarPool getCarPool() {
        return carPool;
    }
}
