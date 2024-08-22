package org.example.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Color;

import java.io.InputStream;
import java.util.Date;
import java.util.List;


public class MyBatisExample1 {
    public static void main(String[] args) {
        int[] callCounts = {25, 50, 100, 150, 250, 500, 1000};
        long[] insertDurations = new long[callCounts.length];
        long[] selectDurations = new long[callCounts.length];

        for (int i = 0; i < callCounts.length; i++) {
            int entityCount = callCounts[i];
            try {
                String resource = "mybatis-config.xml";
                InputStream inputStream = Resources.getResourceAsStream(resource);
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

                long startTime;
                long endTime;

                try (SqlSession session = sqlSessionFactory.openSession()) {
                    OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
                    CatMapper catMapper = session.getMapper(CatMapper.class);

                    startTime = System.nanoTime();
                    for (int j = 0; j < entityCount / 2; j++) {
                        Owner owner = new Owner();
                        owner.setName("Owner " + (j + 1));
                        owner.setBirthDate(new Date(System.currentTimeMillis()));
                        ownerMapper.save(owner);
                        session.commit();

                        Cat cat = new Cat();
                        cat.setName("Cat " + j);
                        cat.setBirthDate(new Date(System.currentTimeMillis()));
                        cat.setBreed("Breed " + j);
                        cat.setColor(Color.values()[j % Color.values().length]);
                        cat.setOwnerId(owner.getId());
                        catMapper.save(cat);
                    }
                    session.commit();

                    endTime = System.nanoTime();
                    insertDurations[i] = endTime - startTime;
                    System.out.println("Время добавления " + entityCount + " сущностей  в наносекундах: " + insertDurations[i]);
                }

                try (SqlSession session = sqlSessionFactory.openSession()) {
                    OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
                    CatMapper catMapper = session.getMapper(CatMapper.class);

                    startTime = System.nanoTime();

                    List<Owner> owners = ownerMapper.getAll();
                    List<Cat> cats = catMapper.getAll();

                    session.commit();

                    endTime = System.nanoTime();
                    selectDurations[i] = endTime - startTime;
                    System.out.println("Время получения " + entityCount + " сущностей  в наносекундах: " + selectDurations[i]);
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
}