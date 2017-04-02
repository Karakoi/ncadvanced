package com.overseer.dao.impl;

import static com.overseer.util.ValidationUtil.validate;

import com.overseer.caching.annotation.CacheableData;
import com.overseer.dao.CrudDao;
import com.overseer.model.AbstractEntity;
import com.overseer.service.QueryService;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Basic implementation of {@link CrudDao} interface.
 *
 * @param <T> entity type.
 */
@NoArgsConstructor
public abstract class CrudDaoImpl<T extends AbstractEntity> implements CrudDao<T, Long> {

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    @Autowired
    private QueryService queryService;

    @Override
    public T save(T entity) {
        validate(entity);
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(entity);
        String insertQuery = this.getInsertQuery();
        if (entity.isNew()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            this.jdbc.update(insertQuery, sqlParameterSource, keyHolder, new String[]{"id"});
            long generatedId = keyHolder.getKey().longValue();
            entity.setId(generatedId);
        } else {
            this.jdbc.update(insertQuery, sqlParameterSource);
        }
        return entity;
    }

    @Override
    public T findOne(Long id) {
        Assert.notNull(id, "id must not be null");
        String findOneQuery = this.getFindOneQuery();
        try {
            return jdbc.queryForObject(findOneQuery,
                    new MapSqlParameterSource("id", id),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity, "entity must not be null");
        this.delete(entity.getId());
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "id must not be null");
        String deleteQuery = this.getDeleteQuery();
        this.jdbc.update(deleteQuery, new MapSqlParameterSource("id", id));
    }


    @Override
    public boolean exists(Long id) {
        Assert.notNull(id, "id must not be null");
        String existsQuery = this.getExistsQuery();
        return this.jdbc.queryForObject(existsQuery,
                new MapSqlParameterSource("id", id), Integer.class) > 0;
    }

    @Override
    @CacheableData
    public List<T> fetchPage(int pageSize, int pageNumber) {
        Assert.state(pageNumber > 0, "Must be greater then 0");
        val parameterSource = new MapSqlParameterSource("limit", pageSize);
        parameterSource.addValue("offset", pageSize * (pageNumber - 1));
        String findAllQuery = this.getFindAllQuery();
        return this.jdbc.query(findAllQuery, parameterSource, this.getMapper());
    }

    @Override
    public Long count() {
        String findCountQuery = getCountQuery();
        return this.jdbc.queryForObject(findCountQuery, new MapSqlParameterSource(), Long.class);
    }

    NamedParameterJdbcOperations jdbc() {
        return this.jdbc;
    }

    QueryService queryService() {
        return this.queryService;
    }

    protected abstract RowMapper<T> getMapper();

    protected abstract String getInsertQuery();

    protected abstract String getFindOneQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getExistsQuery();

    protected abstract String getFindAllQuery();

    protected abstract String getCountQuery();
}
