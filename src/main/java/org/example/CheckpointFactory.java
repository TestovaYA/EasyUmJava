package org.example;

// Интерфейс для создания контрольных пунктов
public interface CheckpointFactory {
    Checkpoint createCheckpoint(String name, double latitude, double longitude, Double penalty);
}
