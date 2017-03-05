package com.overseer.dao.impl;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.model.ProgressStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Implementation of {@link ProgressStatusDao} interface.
 * </p>
 */
@Repository
public class ProgressStatusDaoImpl extends SimpleEntityDaoImpl<ProgressStatus> implements ProgressStatusDao {

    @Override
    protected String getFindByNameQuery() {
        return this.queryService().getQuery("progressStatus.findByName");
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

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("progressStatus.count");
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
