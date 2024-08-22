package org.example.hibernate;

import org.example.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

public class HibernateExample1 {
    public static void main(String[] args) {
        int[] callCounts = {25, 50, 100, 150, 250, 500, 1000};
        long[] insertDurations = new long[callCounts.length];
        long[] selectDurations = new long[callCounts.length];

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        for (int i = 0; i < callCounts.length; i++) {
            int entityCount = callCounts[i];
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
                for (int j = 1; j < entityCount / 2; j++) {
                    Owner owner = new Owner();
                    owner.setName("Owner " + j);
                    owner.setBirthDate(new Date(System.currentTimeMillis()));

                    ownerRepository.save(owner);
                }

                for (int j = 1; j < entityCount / 2; j++) {
                    Cat cat = new Cat();
                    cat.setName("Cat " + j);
                    cat.setBirthDate(new Date(System.currentTimeMillis()));
                    cat.setBreed("Breed " + (j % 5));
                    cat.setColor(Color.values()[j % Color.values().length]);
                    cat.setOwner(session.get(Owner.class, j));

                    catRepository.save(cat);
                }

                entityManager.getTransaction().commit();
                endTime = System.nanoTime();
                insertDurations[i] = endTime - startTime;
                System.out.println("Время добавления " + entityCount + " сущностей в наносекундах: " + insertDurations[i]);

                startTime = System.nanoTime();

                List<Owner> ownerList = ownerRepository.getAll();
                List<Cat> catList = catRepository.getAll();

                endTime = System.nanoTime();
                selectDurations[i] = endTime - startTime;
                System.out.println("Время получения " + entityCount + " сущностей в наносекундах: " + selectDurations[i]);

                entityManager.getTransaction().begin();
                ownerRepository.deleteAll();
                catRepository.deleteAll();
                entityManager.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
