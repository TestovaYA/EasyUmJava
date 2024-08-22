package org.example.jdbc;

import org.example.Color;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class JdbcExample {
    private static final String URL = "jdbc:mysql://localhost:3306/easyum";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            OwnerRepository ownerRepository = new OwnerRepository(connection);
            CatRepository catRepository = new CatRepository(connection);

            for (int i = 0; i < 50; i++) {
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

            System.out.println("All Owners:");
            List<Owner> owners = ownerRepository.getAll();
            for (Owner o : owners) {
                System.out.println("Owner ID: " + o.getId() + ", Name: " + o.getName() + ", Birth Date: " + o.getBirthDate());
                System.out.println("  Cats:");
                List<Cat> cats = catRepository.getAllByParentId(o.getId());
                for (Cat c : cats) {
                    System.out.println("  - Cat ID: " + c.getId() + ", Name: " + c.getName() + ", Color: " + c.getColor());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}