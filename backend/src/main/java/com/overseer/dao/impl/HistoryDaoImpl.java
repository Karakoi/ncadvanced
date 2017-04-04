package com.overseer.dao.impl;

import com.overseer.dao.HistoryDAO;
import com.overseer.model.History;
import com.overseer.model.User;
import com.overseer.service.QueryService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * Implementation of {@link HistoryDAO} interface.
 * </p>
 */
@Transactional
@Repository
@RequiredArgsConstructor
public class HistoryDaoImpl implements HistoryDAO{

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    @Autowired
    private QueryService queryService;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<History> findAllForEntity(Long entityId) {
        Assert.notNull(entityId, "id of entity must not be null");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("entity_id", entityId);
        return jdbc().query(this.queryService().getQuery("history.findAllForEntity"), parameterSource, this.getMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public History findEntity(Long historyId) {
        Assert.notNull(historyId, "id of entity must not be null");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("history_id", historyId);
        return jdbc().queryForObject(this.queryService().getQuery("history.findOne"), parameterSource, this.getMapper());
    }

    /**
     * Mapper for {@link History} object.
     * @return {@link RowMapper} object for {@link History} object.
     */
    private RowMapper<History> getMapper() {
        return (resultSet, i) -> {
            User changer = new User();
            changer.setId(resultSet.getLong("changer_id"));
            changer.setFirstName(resultSet.getString("changer_first_name"));
            changer.setLastName(resultSet.getString("changer_last_name"));

            History history = new History();
            history.setId(resultSet.getLong("id"));
            history.setColumnName(resultSet.getString("column_name"));
            history.setOldValue(resultSet.getString("old_value"));
            history.setNewValue(resultSet.getString("new_value"));
            history.setDemonstrationOfOldValue(resultSet.getString("demonstration_of_old_value"));
            history.setDemonstrationOfNewValue(resultSet.getString("demonstration_of_new_value"));
            history.setDateOfChange(resultSet.getTimestamp("date_of_change").toLocalDateTime());
            history.setChanger(changer);
            history.setRecordId(resultSet.getLong("record_id"));
            return history;
        };
    }

    private NamedParameterJdbcOperations jdbc() {
        return this.jdbc;
    }

    private QueryService queryService() {
        return this.queryService;
    }
}
