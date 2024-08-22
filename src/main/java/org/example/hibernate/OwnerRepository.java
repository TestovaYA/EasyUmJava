package org.example.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class OwnerRepository implements Repository<Owner> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Owner save(Owner entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void deleteById(long id) {
        Owner owner = entityManager.find(Owner.class, id);
        if (owner != null) {
            entityManager.remove(owner);
        }
    }

    @Override
    public void deleteByEntity(Owner entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Owner").executeUpdate();
    }

    @Override
    public Owner update(Owner entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Owner getById(long id) {
        return entityManager.find(Owner.class, id);
    }

    @Override
    public List<Owner> getAll() {
        return entityManager.createQuery("FROM Owner", Owner.class).getResultList();
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
