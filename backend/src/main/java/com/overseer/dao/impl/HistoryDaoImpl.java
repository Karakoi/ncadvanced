package com.overseer.dao.impl;

import com.overseer.dao.HistoryDAO;
import com.overseer.model.History;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * <p>
 * Implementation of {@link HistoryDAO} interface.
 * </p>
 */
@Transactional
@Repository
@RequiredArgsConstructor
public class HistoryDaoImpl extends CrudDaoImpl<History> implements HistoryDAO{

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<History> findAllForEntity(Long entityId) {
        Assert.notNull(entityId, "id of entity must not be null");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("entity_id", entityId);
        return this.jdbc().query(this.queryService().getQuery("history.findAllForEntity"), parameterSource, this.getMapper());
    }

    @Override
    protected RowMapper<History> getMapper() {
        return (resultSet, i) -> {
            History history = new History();
            history.setId(resultSet.getLong("id"));
            history.setColumnName(resultSet.getString("column_name"));
            history.setOldValue(resultSet.getString("old_value"));
            history.setNewValue(resultSet.getString("new_value"));
            Date dateOfLastChanges = resultSet.getDate("date_of_last_changes");
            history.setDateOfLastChange(LocalDateTime.ofInstant(dateOfLastChanges.toInstant(), ZoneId.systemDefault()));
            UserDaoImpl userDao = new UserDaoImpl();
            history.setChanger(userDao.findOne(resultSet.getLong("changer_id")));
            history.setRecordId(resultSet.getLong("record_id"));
            return history;
        };
    }

    @Override
    protected String getInsertQuery() {
        return this.queryService().getQuery("history.insert");
    }

    @Override
    protected String getFindOneQuery() {
        return this.queryService().getQuery("history.findOne");
    }

    @Override
    protected String getDeleteQuery() {
        return this.queryService().getQuery("user.delete");
    }

    @Override
    protected String getExistsQuery() {
        return this.queryService().getQuery("history.exists");
    }

    @Override
    protected String getFindAllQuery() {
        return this.queryService().getQuery("history.findAll");
    }

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("history.count");
    }
}
