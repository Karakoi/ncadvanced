package com.overseer.service;

/**
 * The <code>QueryService</code> interface provides means to inject SQL queries
 * from property file to desired java bean.
 */
public interface QueryService {

    /**
     * Fetches SQL query by provided key-name.
     *
     * @param name name of the SQL query.
     * @return SQL query.
     * @throws IllegalStateException if the query name cannot be resolved
     */
    String getQuery(String name);
}
