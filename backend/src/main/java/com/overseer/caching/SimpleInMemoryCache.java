package com.overseer.caching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


//TODO Change value type tu future
//TODO Add new annotation for data update
/**
 * Simple cache for dao, if dao has annotation {@link CacheableData} it's gonna be stored in map
 * and returned it next request.
 *
 * @param <K> Key for storing in map which identify method invoking.
 * @param <V> Data or array of date fetched from db.
 */
public class SimpleInMemoryCache<K, V> {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleInMemoryCache.class);
    private static final int SECOND = 1000;

    private Map<K, V> cache = new ConcurrentHashMap<>();

    public SimpleInMemoryCache(long lifeTime) {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(lifeTime * SECOND);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                cleanup();
            }
        });
        t.setDaemon(true);
        t.start();
        LOG.debug("Cache has been created");
    }

    /**
     * Get data from cache by key.
     * @param key identifier of method and params.
     * @return cached data or null if it doesn't exists.
     */
    public V getData(K key) {
        LOG.debug("Getting date from cache with key {}", key);
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            return null;
        }
    }

    public void cleanup() {
        LOG.debug("Cleaning cache");
        cache.clear();

    }

    public int getSize() {
        return cache.size();
    }

    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    public void remove(V key) {
        cache.remove(key);
    }

    public void put(K key, V value) {
        LOG.debug("Adding date to cache with key: {}", key);
        cache.put(key, value);
    }

}
