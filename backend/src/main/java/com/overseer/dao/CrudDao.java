package com.overseer.dao;

import com.overseer.caching.CacheableData;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for generic CRUD operations.
 *
 * @param <T>  entity type.
 * @param <ID> entity identifier type.
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
     * Returns list of entities for provided page.
     *
     * @param pageSize   number of entities on the page.
     * @param pageNumber number of page.
     * @return a list of entities.
     */
    @CacheableData
    List<T> fetchPage(int pageSize, int pageNumber);

    /**
     * Returns number of entities of type <code>T</code>.
     *
     * @return number of entities of type <code>T</code>.
     */
    Long count();
}
