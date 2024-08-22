package org.example.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CatRepository implements Repository<Cat> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Cat save(Cat entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void deleteById(long id) {
        Cat cat = entityManager.find(Cat.class, id);
        if (cat != null) {
            entityManager.remove(cat);
        }
    }

    @Override
    public void deleteByEntity(Cat entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Cat").executeUpdate();
    }

    @Override
    public Cat update(Cat entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Cat getById(long id) {
        return entityManager.find(Cat.class, id);
    }

    @Override
    public List<Cat> getAll() {
        return entityManager.createQuery("FROM Cat", Cat.class).getResultList();
    }

    public List<Cat> getAllByParentId(Long ownerId) {
        return entityManager.createQuery("SELECT c FROM Cat c WHERE c.owner.id = :ownerId", Cat.class)
                .setParameter("ownerId", ownerId)
                .getResultList();
    }
}
