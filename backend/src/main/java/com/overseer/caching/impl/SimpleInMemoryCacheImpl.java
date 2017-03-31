package com.overseer.caching.impl;

import com.overseer.caching.SimpleInMemoryCache;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;


/**
 * Simple cache for dao, if dao has annotation CacheableData it's gonna be stored in map
 * and returned it next request.
 *
 * @param <K> Key for storing in map which identify method invoking.
 * @param <V> Data or array of date fetched from db.
 */
public class SimpleInMemoryCacheImpl<K, V> implements SimpleInMemoryCache<K, V> {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleInMemoryCacheImpl.class);
    private static final int SECOND = 1000;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    private Map<K, Future<V>> cache = new ConcurrentHashMap<>();


    public SimpleInMemoryCacheImpl(long lifeTime) {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(lifeTime * SECOND);
                } catch (InterruptedException ex) {
                    LOG.debug("Something in cache gone wrong");
                    ex.printStackTrace();
                }
                cleanup();
            }
        });
        t.setDaemon(true);
        executorService.execute(t);
        LOG.debug("Cache has been created");
    }

    /**
     * Save value as a future if it not exists.
     * @param key just a key for value.
     * @param callable value wrapped it callable so it can be stored as future.
     * @return future of value if it exists or just added.
     */
    private Future<V> createIfAbsent(K key, final Callable<V> callable) {
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

    @Override
    public V getData(K key) {
        LOG.debug("Getting date from cache with key {}", key);
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
        LOG.debug("Cleaning cache");
        cache.clear();
    }

    @Override
    public  int getSize() {
        return cache.size();
    }

    @Override
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    @Override
    public void remove(V key) {
        cache.remove(key);
    }

    @Override
    public void put(K key, V value) {
        LOG.debug("Adding date to cache with key: {}", key);
        createIfAbsent(key, () -> value);
    }

}
