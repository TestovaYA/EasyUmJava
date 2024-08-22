package org.example.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Color;

import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class MyBatisExample {
    public static final int ENTITY_COUNT = 100;

    public static void main(String[] args) {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            long startTime;
            long endTime;
            long duration;

            try (SqlSession session = sqlSessionFactory.openSession()) {
                OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
                CatMapper catMapper = session.getMapper(CatMapper.class);

                startTime = System.nanoTime();
                for (int i = 0; i < ENTITY_COUNT/2; i++) {
                    Owner owner = new Owner();
                    owner.setName("Owner " + (i + 1));
                    owner.setBirthDate(new Date(System.currentTimeMillis()));
                    ownerMapper.save(owner);
                    session.commit();

                    Cat cat = new Cat();
                    cat.setName("Cat " + i);
                    cat.setBirthDate(new Date(System.currentTimeMillis()));
                    cat.setBreed("Breed " + i);
                    cat.setColor(Color.values()[i % Color.values().length]);
                    cat.setOwnerId(owner.getId());
                    catMapper.save(cat);
                }
                session.commit();

                endTime = System.nanoTime();
                duration = endTime - startTime;
                System.out.println("Время добавления " + ENTITY_COUNT + " сущностей  в наносекундах: " + duration);
            }

            try (SqlSession session = sqlSessionFactory.openSession()) {
                OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
                CatMapper catMapper = session.getMapper(CatMapper.class);

                startTime = System.nanoTime();

                List<Owner> owners = ownerMapper.getAll();
                List<Cat> cats = catMapper.getAll();

                session.commit();

                endTime = System.nanoTime();
                duration = endTime - startTime;
                System.out.println("Время получения " + ENTITY_COUNT + " сущностей  в наносекундах: " + duration);
            }

            try (SqlSession session = sqlSessionFactory.openSession()) {
                OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
                CatMapper catMapper = session.getMapper(CatMapper.class);

                ownerMapper.deleteAll();
                catMapper.deleteAll();
                session.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}