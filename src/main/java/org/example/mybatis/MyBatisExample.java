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
    public static void main(String[] args) {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            try (SqlSession session = sqlSessionFactory.openSession()) {
                OwnerMapper ownerMapper = session.getMapper(OwnerMapper.class);
                CatMapper catMapper = session.getMapper(CatMapper.class);

                for (int i = 0; i < 50; i++) {
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

                List<Owner> owners = ownerMapper.getAll();
                for (Owner o : owners) {
                    System.out.println("Owner ID: " + o.getId() + ", Name: " + o.getName() + ", Birth Date: " + o.getBirthDate());
                    System.out.println("  Cats:");
                    List<Cat> cats = catMapper.getAllByOwnerId(o.getId());
                    for (Cat c : cats) {
                        System.out.println("  - Cat ID: " + c.getId() + ", Name: " + c.getName() + ", Color: " + c.getColor());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}