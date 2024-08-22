package org.example.mybatis;

import java.util.List;

public interface OwnerMapper {
    void save(Owner owner);

    void deleteById(Integer id);

    void deleteByEntity(Owner owner);

    void deleteAll();

    Owner update(Owner owner);

    Owner getById(Integer id);

    List<Owner> getAll();
}
