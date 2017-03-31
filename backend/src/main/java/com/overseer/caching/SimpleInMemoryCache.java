package com.overseer.caching;

/**
 * Simple cache for dao, if dao has annotation CacheableData it's gonna be stored in map
 * and returned it next request.
 *
 * @param <K> Key for storing in map which identify method invoking.
 * @param <V> Data or array of date fetched from db.
 */
public interface SimpleInMemoryCache<K, V> {
    /**
     * Get data from cache by key.
     *
     * @param key identifier of method and params.
     * @return cached data or null if it doesn't exists.
     */
    V getData(K key);

    void cleanup();

    int getSize();

    boolean contains(K key);

    void remove(V key);

    /**
     * Adding value to map.
     * @param key just a key for value.
     * @param value value which will be stored.
     */
    void put(K key, V value);
}
