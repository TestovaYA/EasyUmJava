package org.example.hibernate;

import org.example.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class HibernateExample {
    public static final int ENTITY_COUNT = 100;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); var session = sessionFactory.openSession()) {
            entityManager.getTransaction().begin();

            OwnerRepository ownerRepository = new OwnerRepository();
            ownerRepository.setEntityManager(entityManager);
            CatRepository catRepository = new CatRepository();
            catRepository.setEntityManager(entityManager);

            long startTime;
            long endTime;
            long duration;

            startTime = System.nanoTime();
            for (int i = 1; i < ENTITY_COUNT / 2; i++) {
                Owner owner = new Owner();
                owner.setName("Owner " + i);
                owner.setBirthDate(new Date(System.currentTimeMillis()));

                ownerRepository.save(owner);
            }

            for (int i = 1; i < ENTITY_COUNT / 2; i++) {
                Cat cat = new Cat();
                cat.setName("Cat " + i);
                cat.setBirthDate(new Date(System.currentTimeMillis()));
                cat.setBreed("Breed " + (i % 5));
                cat.setColor(Color.values()[i % Color.values().length]);
                cat.setOwner(session.get(Owner.class, i));

                catRepository.save(cat);
            }

            entityManager.getTransaction().commit();
            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.println("Время добавления " + ENTITY_COUNT + " сущностей  в наносекундах: " + duration);

            startTime = System.nanoTime();

            List<Owner> ownerList = ownerRepository.getAll();
            List<Cat> catList = catRepository.getAll();

            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.println("Время получения " + ENTITY_COUNT + " сущностей  в наносекундах: " + duration);

            entityManager.getTransaction().begin();
            ownerRepository.deleteAll();
            catRepository.deleteAll();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
