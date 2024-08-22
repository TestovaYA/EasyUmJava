package org.example.mybatis;

import java.util.List;

public interface CatMapper {
    void save(Cat cat);

    void deleteById(Integer id);

    void deleteByEntity(Cat cat);

    void deleteAll();

    Cat update(Cat cat);

    Cat getById(Integer id);

    List<Cat> getAll();

    List<Cat> getAllByOwnerId(Integer ownerId);
}