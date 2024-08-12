package org.example;

// Конкретный фабричный класс для контрольных пунктов
public class ConcreteCheckpointFactory implements CheckpointFactory {
    @Override
    public Checkpoint createCheckpoint(String name, double latitude, double longitude, Double penalty) {
        if (penalty == null) {
            return new MandatoryCheckpoint(name, latitude, longitude);
        } else {
            return new OptionalCheckpoint(name, latitude, longitude, penalty);
        }
    }
}