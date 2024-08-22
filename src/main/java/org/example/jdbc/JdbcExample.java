package org.example.jdbc;

import org.example.Color;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class JdbcExample {
    private static final String URL = "jdbc:mysql://localhost:3306/easyum";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static final int ENTITY_COUNT = 100;

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            OwnerRepository ownerRepository = new OwnerRepository(connection);
            CatRepository catRepository = new CatRepository(connection);

            long startTime;
            long endTime;
            long duration;

            startTime = System.nanoTime();
            for (int i = 0; i < ENTITY_COUNT/2; i++) {
                Owner owner = new Owner();
                owner.setName("Owner " + (i + 1));
                owner.setBirthDate(new java.util.Date(System.currentTimeMillis()));
                owner = ownerRepository.save(owner);

                Cat cat = new Cat();
                cat.setName("Cat " + i);
                cat.setBirthDate(new Date(System.currentTimeMillis()));
                cat.setBreed("Breed " + i);
                cat.setColor(Color.values()[i % Color.values().length]);
                cat.setOwnerId(owner.getId());
                catRepository.save(cat);
            }

            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.println("Время добавления " + ENTITY_COUNT + " сущностей  в наносекундах: " + duration);

            startTime = System.nanoTime();

            List<Owner> owners = ownerRepository.getAll();
            List<Cat> cats = catRepository.getAll();

            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.println("Время получения " + ENTITY_COUNT + " сущностей  в наносекундах: " + duration);

            ownerRepository.deleteAll();
            catRepository.deleteAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}