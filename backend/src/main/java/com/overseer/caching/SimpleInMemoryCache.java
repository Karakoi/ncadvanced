package com.overseer.caching;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;


//TODO Change value type tu future
//TODO Make cache clean more intellectual

/**
 * Simple cache for dao, if dao has annotation {@link CacheableData} it's gonna be stored in map
 * and returned it next request.
 *
 * @param <K> Key for storing in map which identify method invoking.
 * @param <V> Data or array of date fetched from db.
 */
class SimpleInMemoryCache<K, V> {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleInMemoryCache.class);
    private static final int SECOND = 1000;

    private Map<K, Future<V>> cache = new ConcurrentHashMap<>();

    /**
     * Im javadoc.
     * @param key Im javadoc.
     * @param callable Im javadoc.
     * @return Im javadoc.
     */
    Future<V> createIfAbsent(K key, final Callable<V> callable) {
        Future<V> future = cache.get(key);
        if (future == null) {
            final FutureTask<V> futureTask = new FutureTask<>(callable);
            future = cache.putIfAbsent(key, futureTask);
            if (future == null) {
                future = futureTask;
                futureTask.run();
            }
        }
        return future;
    }

    SimpleInMemoryCache(long lifeTime) {
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
     *
     * @param key identifier of method and params.
     * @return cached data or null if it doesn't exists.
     */
    V getData(K key) {
        LOG.debug("Getting date from cache with key {}", key);
        V value = null;
        val future = cache.getOrDefault(key, null);
        try {
            value = future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            return value;
        }
    }

    void cleanup() {
        LOG.debug("Cleaning cache");
        cache.clear();
    }

    int getSize() {
        return cache.size();
    }

    boolean contains(K key) {
        return cache.containsKey(key);
    }

    void remove(V key) {
        cache.remove(key);
    }

    /**
     * Im javadoc.
     * @param key Im javadoc.
     * @param value Im javadoc.
     */
    void put(K key, V value) {
        LOG.debug("Adding date to cache with key: {}", key);
        createIfAbsent(key, () -> value);
    }

}
