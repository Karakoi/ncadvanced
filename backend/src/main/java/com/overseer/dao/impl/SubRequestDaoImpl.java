package com.overseer.dao.impl;

import com.overseer.dao.SubRequestDao;
import com.overseer.model.SubRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @{inheritDoc}.
 */
@RequiredArgsConstructor
public class SubRequestDaoImpl implements SubRequestDao {
    private static final String SQL_SELECT_ALL = "SELECT * FROM sub_request";
    private static final String SQL_SELECT_ONE = SQL_SELECT_ALL + "WHERE id = :id";
    private static final String SQL_SELECT_BY_REQUEST_ID = SQL_SELECT_ALL + "WHERE request_id = :requestId";
    private static final String SQL_DELETE_ONE = "DELETE FROM sub_request WHERE id = :id";
    private static final String SQL_EXISTS = "SELECT count(id) FROM sub_request WHERE id = :id";
    private static final String SQL_SAVE = "INSERT INTO sub_request (id,title,description,request_id,previous_sub_request_id,history_detail_id"
            + "VALUES (:id,:title,:description,:request_id,:previous_sub_request_id,:history_detail_id))";

    @Autowired
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public SubRequest save(SubRequest entity) {
        MapSqlParameterSource map = new MapSqlParameterSource("id", entity.getId());
        map.addValue("title", entity.getTitle());
        map.addValue("description", entity.getDescription());
        map.addValue("request_id", entity.getRequestId());
        map.addValue("previous_sub_request_id", entity.getPreviousSubRequestId());
        map.addValue("history_detail_id", entity.getHistoryDetailId());
        jdbc.update(SQL_SAVE, map);
        return entity;
    }

    @Override
    public SubRequest findOne(Long id) {
        return jdbc.queryForObject(SQL_SELECT_ONE, new MapSqlParameterSource("id", id),
                new SubRequestRowMapper());
    }

    @Override
    public void delete(SubRequest entity) {
        Assert.notNull(entity);
        delete(entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbc.update(SQL_DELETE_ONE, new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean exists(Long id) {
        Integer count = jdbc.queryForObject(SQL_EXISTS, new MapSqlParameterSource("id", id), Integer.class);
        return count != null && count == 1;
    }

    @Override
    public List<SubRequest> findAll() {
        return jdbc.query(SQL_SELECT_ALL, new SubRequestRowMapper());
    }

    @Override
    public List<SubRequest> findByRequest(Long requestId) {
        return jdbc.query(SQL_SELECT_BY_REQUEST_ID, new MapSqlParameterSource("requestId", requestId),
                new SubRequestRowMapper());
    }

    /**
     * Class witch map result set to entity.
     */
    private static final class SubRequestRowMapper implements RowMapper<SubRequest> {
        /**
         *Just implementation of spring interface.
         */
        public SubRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            SubRequest subRequest = new SubRequest();
            subRequest.setId(rs.getLong("id"));
            subRequest.setPreviousSubRequestId(rs.getLong("previous_sub_request_id"));
            subRequest.setRequestId(rs.getLong("request_id"));
            subRequest.setTitle(rs.getString("title"));
            subRequest.setDescription(rs.getString("description"));
            subRequest.setHistoryDetailId(rs.getLong("history_detail_id"));
            return subRequest;
        }
    }
}
