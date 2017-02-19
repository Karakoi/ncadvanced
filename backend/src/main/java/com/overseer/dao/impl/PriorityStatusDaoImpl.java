package com.overseer.dao.impl;

import com.overseer.dao.PriorityStatusDao;
import com.overseer.model.PriorityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Romanova on 18.02.2017.
 */
@Repository
public class PriorityStatusDaoImpl implements PriorityStatusDao{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     @{inheritDoc}
     */
    @Transactional(readOnly=true)
    @Override
    public PriorityStatus getPriorityStatusById(Long priorityStatusId){
        String sql = "select id, name from priority_status where id = :priorityStatusId";

        SqlParameterSource namedParameters = new MapSqlParameterSource("priorityStatusId", priorityStatusId);
        return this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new PriorityStatusMapper());
    }

    /**
     @{inheritDoc}
     */
    @Transactional(readOnly=true)
    @Override
    public List<PriorityStatus> findAllPriorityStatuses() {
        return this.namedParameterJdbcTemplate.query("select id, name from priority_status", new PriorityStatusMapper());
    }

    private static final class PriorityStatusMapper implements RowMapper<PriorityStatus> {
        @Override
        public PriorityStatus mapRow(ResultSet resultSet, int i) throws SQLException {
            PriorityStatus priorityStatus = new PriorityStatus();
            priorityStatus.setId(resultSet.getLong("id"));
            priorityStatus.setName(resultSet.getString("name"));
            return priorityStatus;
        }
    }
}
