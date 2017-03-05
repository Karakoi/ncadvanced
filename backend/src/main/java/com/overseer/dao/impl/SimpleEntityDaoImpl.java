package com.overseer.dao.impl;

import com.overseer.dao.SimpleEntityDao;
import com.overseer.model.AbstractEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Basic implementation of interface.
 *
 * @param <T> entity type.
 */
public abstract class SimpleEntityDaoImpl<T extends AbstractEntity> extends CrudDaoImpl<T> implements SimpleEntityDao<T> {
    /**
     * {@inheritDoc}.
     */
    @Override
    public T findByName(String name) {
        Assert.notNull(name, "name must not be null");
        try {
            return this.jdbc().queryForObject(this.getFindByNameQuery(),
                    new MapSqlParameterSource("name", name),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<T> findAll() {
        return this.jdbc().query(this.getFindAllQuery(), this.getMapper());
    }

    protected abstract String getFindByNameQuery();

}
