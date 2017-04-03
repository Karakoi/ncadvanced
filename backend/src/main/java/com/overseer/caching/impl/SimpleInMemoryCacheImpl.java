package com.overseer.caching.impl;

import com.overseer.caching.SimpleInMemoryCache;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Map;
import java.util.concurrent.*;


/**
 * Simple cache for dao, if dao has annotation CacheableData it's gonna be stored in map
 * and returned it next request.
 *
 * @param <K> Key for storing in map which identify method invoking.
 * @param <V> Data or array of date fetched from db.
 */
@Slf4j
public class SimpleInMemoryCacheImpl<K, V> implements SimpleInMemoryCache<K, V> {

    private Map<K, Future<V>> cache = new ConcurrentHashMap<>();

    public SimpleInMemoryCacheImpl(long lifeTime) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::cleanup, lifeTime, lifeTime, TimeUnit.SECONDS);
        log.debug("Cache has been created");
    }

    /**
     * Save value as a future if it not exists.
     *
     * @param key      just a key for value.
     * @param callable value wrapped it callable so it can be stored as future.
     */
    private void createIfAbsent(K key, final Callable<V> callable) {
        Future<V> future = cache.get(key);
        if (future == null) {
            final FutureTask<V> futureTask = new FutureTask<>(callable);
            future = cache.putIfAbsent(key, futureTask);
            if (future == null) {
                futureTask.run();
            }
        }
    }

    @Override
    public V getData(K key) {
        V value = null;
        val future = cache.getOrDefault(key, null);
        try {
            value = future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }


    @Override
    public void cleanup() {
        log.debug("Cleaning cache");
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public void put(K key, V value) {
        createIfAbsent(key, () -> value);
    }

}
