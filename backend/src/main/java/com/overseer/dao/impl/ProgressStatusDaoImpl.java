package com.overseer.dao.impl;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.model.ProgressStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * <p>
 * Implementation of {@link ProgressStatusDao} interface.
 * </p>
 */
@Repository
public class ProgressStatusDaoImpl extends CrudDaoImpl<ProgressStatus> implements ProgressStatusDao {

    /**
     * {@inheritDoc}.
     */
    @Override
    public ProgressStatus findByName(String name) {
        Assert.notNull(name, "name must not be null");
        try {
            return this.jdbc().queryForObject(this.queryService().getQuery("progressStatus.findByName"),
                    new MapSqlParameterSource("progressStatusName", name),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    protected String getInsertQuery() {
        return this.queryService().getQuery("progressStatus.insert");
    }

    @Override
    protected String getFindOneQuery() {
        return this.queryService().getQuery("progressStatus.findOne");
    }

    @Override
    protected String getDeleteQuery() {
        return this.queryService().getQuery("progressStatus.delete");
    }

    @Override
    protected String getExistsQuery() {
        return this.queryService().getQuery("progressStatus.exists");
    }

    @Override
    protected String getFindAllQuery() {
        return this.queryService().getQuery("progressStatus.findAll");
    }

    /**
     * Gets {@link RowMapper} implementation for {@link ProgressStatus} entity.
     *
     * @return {@link RowMapper} implementation for {@link ProgressStatus} entity.
     */
    @Override
    protected RowMapper<ProgressStatus> getMapper() {
        return (resultSet, i) -> {
            ProgressStatus progressStatus = new ProgressStatus(resultSet.getString("name"));
            progressStatus.setId(resultSet.getLong("id"));
            return progressStatus;
        };
    }
}
