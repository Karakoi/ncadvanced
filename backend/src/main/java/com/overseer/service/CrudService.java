package com.overseer.service;

import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.exception.entity.NoSuchEntityException;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for generic CRUD operations.
 *
 * @param <T>  entity type.
 * @param <ID> entity identifier type.
 */
public interface CrudService<T, ID extends Serializable> {
    /**
     * Create a given entity.
     *
     * @param entity must not be {@literal null}.
     * @return the crated entity.
     */
    T create(T entity) throws EntityAlreadyExistsException;

    /**
     * Update a given entity.
     *
     * @param entity must not be {@literal null}.
     * @return the updated entity.
     */
    T update(T entity) throws NoSuchEntityException;

    /**
     * Fetches entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return entity with the given id or {@literal null} if none found.
     */
    T findOne(ID id) throws NoSuchEntityException;

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
    List<T> fetchPage(int pageNumber);
}
