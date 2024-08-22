package org.example.hibernate;

import org.example.Color;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class HibernateExample {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory(); var session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            for (int i = 1; i <= 50; i++) {
                Owner owner = new Owner();
                owner.setName("Owner " + i);
                owner.setBirthDate(new Date(System.currentTimeMillis()));

                session.save(owner);
            }

            for (int i = 1; i <= 50; i++) {
                Cat cat = new Cat();
                cat.setName("Cat " + i);
                cat.setBirthDate(new Date(System.currentTimeMillis()));
                cat.setBreed("Breed " + (i % 5));
                cat.setColor(Color.values()[i % Color.values().length]);
                cat.setOwner(session.get(Owner.class, i));

                session.save(cat);
            }

            transaction.commit();

            System.out.println("All Owners:");
            Query<Owner> ownerQuery = session.createQuery("FROM Owner", Owner.class);
            List<Owner> ownerList = ownerQuery.getResultList();
            for (Owner owner : ownerList) {
                System.out.println("Owner ID: " + owner.getId() + ", Name: " + owner.getName() + ", BirthDate: " + owner.getBirthDate());
            }

            System.out.println("All Cats:");
            Query<Cat> catQuery = session.createQuery("FROM Cat", Cat.class);
            List<Cat> catList = catQuery.getResultList();
            for (Cat cat : catList) {
                System.out.println("Cat ID: " + cat.getId() + ", Name: " + cat.getName() + ", Breed: " + cat.getBreed() + ", Color: " + cat.getColor() + ", Owner ID: " + cat.getOwner().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
