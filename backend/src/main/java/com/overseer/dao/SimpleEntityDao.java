package com.overseer.dao;

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
}
