package org.example.jdbc;

import org.example.Color;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class JdbcExample1 {
    private static final String URL = "jdbc:mysql://localhost:3306/easyum";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        int[] callCounts = {25, 50, 100, 150, 250, 500, 1000};
        long[] insertDurations = new long[callCounts.length];
        long[] selectDurations = new long[callCounts.length];

        for (int i = 0; i < callCounts.length; i++) {
            int entityCount = callCounts[i];
            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                OwnerRepository ownerRepository = new OwnerRepository(connection);
                CatRepository catRepository = new CatRepository(connection);

                long startTime;
                long endTime;

                startTime = System.nanoTime();
                for (int j = 0; j < entityCount / 2; j++) {
                    Owner owner = new Owner();
                    owner.setName("Owner " + (j + 1));
                    owner.setBirthDate(new Date(System.currentTimeMillis()));
                    owner = ownerRepository.save(owner);

                    Cat cat = new Cat();
                    cat.setName("Cat " + j);
                    cat.setBirthDate(new Date(System.currentTimeMillis()));
                    cat.setBreed("Breed " + j);
                    cat.setColor(Color.values()[j % Color.values().length]);
                    cat.setOwnerId(owner.getId());
                    catRepository.save(cat);
                }

                endTime = System.nanoTime();
                insertDurations[i] = endTime - startTime;
                System.out.println("Время добавления " + entityCount + " сущностей  в наносекундах: " + insertDurations[i]);

                startTime = System.nanoTime();

                List<Owner> owners = ownerRepository.getAll();
                List<Cat> cats = catRepository.getAll();

                endTime = System.nanoTime();
                selectDurations[i] = endTime - startTime;
                System.out.println("Время получения " + entityCount + " сущностей  в наносекундах: " + selectDurations[i]);

                ownerRepository.deleteAll();
                catRepository.deleteAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}