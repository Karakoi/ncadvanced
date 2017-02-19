package com.overseer.dao.impl;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.model.ProgressStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 @{inheritDoc}.
 */
public class ProgressStatusDaoImpl implements ProgressStatusDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     @{inheritDoc}.
     */
    @Transactional(readOnly = true)
    @Override
    public List<ProgressStatus> findAllProgressStatuses() {
        return this.namedParameterJdbcTemplate.query("select id, name from priority_status", new ProgressStatusMapper());
    }

    /**
     @{inheritDoc}.
     */
    @Transactional(readOnly = true)
    @Override
    public ProgressStatus getProgressStatusById(Long progressStatusId) {
        String sql = "select id, name from progress_status where id = :progressStatusId";
        SqlParameterSource namedParameters = new MapSqlParameterSource("progressStatusId", progressStatusId);
        return this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new ProgressStatusMapper());
    }

    /**
     * The <code>ProgressStatusMapper</code> class represents mapper for ProgressStatus {@link ProgressStatus} object.
     */
    private static final class ProgressStatusMapper implements RowMapper<ProgressStatus> {
        @Override
        public ProgressStatus mapRow(ResultSet resultSet, int i) throws SQLException {
            ProgressStatus progressStatus = new ProgressStatus();
            progressStatus.setId(resultSet.getLong("id"));
            progressStatus.setName(resultSet.getString("name"));
            return progressStatus;
        }
    }
}
