package com.overseer.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for generic CRUD operations.
 */
public interface CrudDao<T, ID extends Serializable> {

    /**
     * Saves a given entity.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity.
     */
    T save(T entity);

    /**
     * Fetches entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return entity with the given id or {@literal null} if none found.
     */
    T findOne(ID id);

    /**
     * Deletes a given entity.
     *
     * @param entity must not be {@literal null}.
     */
    void delete(T entity);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     */
    void delete(ID id);

    /**
     * Checks whether entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if entity with the given id exists, {@literal false} otherwise.
     */
    boolean exists(ID id);

    /**
     * Returns all entities of the type.
     *
     * @return all entities.
     */
    List<T> findAll();
}
