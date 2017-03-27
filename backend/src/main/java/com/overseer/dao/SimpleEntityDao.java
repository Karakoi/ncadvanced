package com.overseer.dao;

import com.overseer.caching.CacheableData;

import java.util.List;

/**
 * Interface for generic operations for simple entities.
 *
 * @param <T>   entity type.
 */
public interface SimpleEntityDao<T> extends CrudDao<T, Long> {
    /**
     * Fetches entity by its name.
     *
     * @param name must not be {@literal null}.
     * @return entity with the given name or {@literal null} if none found.
     */
    T findByName(String name);

    /**
     * Fetches all entities.
     *
     * @return all entities.
     */
    @CacheableData
    List<T> findAll();
}
