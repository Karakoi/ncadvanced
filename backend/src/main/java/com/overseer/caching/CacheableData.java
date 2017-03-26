package com.overseer.caching;


import java.lang.annotation.*;

/**
 * Annotation for methods which fetch some data from database.
 * It provide in memory caching.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheableData {
}
