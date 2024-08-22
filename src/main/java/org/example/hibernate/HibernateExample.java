package org.example.hibernate;

import org.example.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class HibernateExample {
    public static final int ENTITY_COUNT = 100;

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); var session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            long startTime;
            long endTime;
            long duration;

            startTime = System.nanoTime();
            for (int i = 1; i <ENTITY_COUNT/2; i++) {
                Owner owner = new Owner();
                owner.setName("Owner " + i);
                owner.setBirthDate(new Date(System.currentTimeMillis()));

                session.save(owner);
            }

            for (int i = 1; i <ENTITY_COUNT/2; i++) {
                Cat cat = new Cat();
                cat.setName("Cat " + i);
                cat.setBirthDate(new Date(System.currentTimeMillis()));
                cat.setBreed("Breed " + (i % 5));
                cat.setColor(Color.values()[i % Color.values().length]);
                cat.setOwner(session.get(Owner.class, i));

                session.save(cat);
            }

            transaction.commit();
            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.println("Время добавления " + ENTITY_COUNT + " сущностей  в наносекундах: " + duration);

            startTime = System.nanoTime();

            Query<Owner> ownerQuery = session.createQuery("FROM Owner", Owner.class);
            List<Owner> ownerList = ownerQuery.getResultList();

            Query<Cat> catQuery = session.createQuery("FROM Cat", Cat.class);
            List<Cat> catList = catQuery.getResultList();

            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.println("Время получения " + ENTITY_COUNT + " сущностей  в наносекундах: " + duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
