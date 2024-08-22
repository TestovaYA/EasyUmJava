package org.example.hibernate;

import javax.persistence.EntityManager;
import java.util.List;

public interface Repository<T> {
    T save(T entity);

    void deleteById(long id);

    void deleteByEntity(T entity);

    void deleteAll();

    T update(T entity);

    T getById(long id);

    List<T> getAll();

    void setEntityManager(EntityManager entityManager);
}
