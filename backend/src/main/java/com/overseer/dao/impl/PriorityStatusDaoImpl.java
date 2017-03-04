package com.overseer.dao.impl;

import com.overseer.dao.PriorityStatusDao;
import com.overseer.model.PriorityStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * <p>
 * Implementation of {@link PriorityStatusDao} interface.
 * </p>
 */
@Repository
public class PriorityStatusDaoImpl extends CrudDaoImpl<PriorityStatus> implements PriorityStatusDao {

    /**
     * {@inheritDoc}.
     */
    @Override
    public PriorityStatus findByName(String name) {
        Assert.notNull(name, "name must not be null");
        try {
            return this.jdbc().queryForObject(this.queryService().getQuery("priorityStatus.findByName"),
                    new MapSqlParameterSource("priorityStatusName", name),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    protected String getInsertQuery() {
        return this.queryService().getQuery("priorityStatus.insert");
    }

    @Override
    protected String getFindOneQuery() {
        return this.queryService().getQuery("priorityStatus.findOne");
    }

    @Override
    protected String getDeleteQuery() {
        return this.queryService().getQuery("priorityStatus.delete");
    }

    @Override
    protected String getExistsQuery() {
        return this.queryService().getQuery("priorityStatus.exists");
    }

    @Override
    protected String getFindAllQuery() {
        return this.queryService().getQuery("priorityStatus.findAll");
    }

    @Override
    protected String getCountQuery() {
        return queryService().getQuery("priorityStatus.count");
    }

    /**
     * Gets {@link RowMapper} implementation for {@link PriorityStatus} entity.
     *
     * @return {@link RowMapper} implementation for {@link PriorityStatus} entity.
     */
    @Override
    protected RowMapper<PriorityStatus> getMapper() {
        return (resultSet, i) -> {
            PriorityStatus priorityStatus = new PriorityStatus(resultSet.getString("name"));
            priorityStatus.setId(resultSet.getLong("id"));
            return priorityStatus;
        };
    }

}
