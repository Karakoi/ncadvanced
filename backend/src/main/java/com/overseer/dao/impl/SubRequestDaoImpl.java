package com.overseer.dao.impl;

import com.overseer.dao.SubRequestDao;
import com.overseer.model.SubRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 * Implementation of {@link SubRequestDao} interface.
 * </p>
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class SubRequestDaoImpl implements SubRequestDao {

    private static final String SQL_SELECT_ALL = "SELECT * FROM sub_request";

    private static final String SQL_SELECT_ONE = SQL_SELECT_ALL + "WHERE id = :id";

    private static final String SQL_SELECT_BY_REQUEST_ID = SQL_SELECT_ALL + "WHERE request_id = :requestId";

    private static final String SQL_DELETE_ONE = "DELETE FROM sub_request WHERE id = :id";

    private static final String SQL_EXISTS = "SELECT count(id) FROM sub_request WHERE id = :id";

    private static final String SQL_SAVE =
            "INSERT INTO sub_request (id,title,description,request_id VALUES (:id,:title,:description,:request_id))";

    private final NamedParameterJdbcOperations jdbc;

    /**
     * {@inheritDoc}.
     */
    @Override
    public SubRequest save(SubRequest entity) {
        MapSqlParameterSource map = new MapSqlParameterSource("id", entity.getId());
        map.addValue("title", entity.getTitle());
        map.addValue("description", entity.getDescription());
        map.addValue("request_id", entity.getRequestId());
        jdbc.update(SQL_SAVE, map);
        return entity;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public SubRequest findOne(Long id) {
        Assert.notNull(id);
        try {
            return jdbc.queryForObject(SQL_SELECT_ONE,
                    new MapSqlParameterSource("id", id),
                    new SubRequestRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(SubRequest entity) {
        Assert.notNull(entity);
        delete(entity.getId());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        jdbc.update(SQL_DELETE_ONE, new MapSqlParameterSource("id", id));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        Assert.notNull(id);
        Integer count = jdbc.queryForObject(SQL_EXISTS, new MapSqlParameterSource("id", id), Integer.class);
        return count != null && count == 1;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<SubRequest> findAll() {
        return jdbc.query(SQL_SELECT_ALL, new SubRequestRowMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<SubRequest> findByRequest(Long requestId) {
        Assert.notNull(requestId);
        return jdbc.query(SQL_SELECT_BY_REQUEST_ID, new MapSqlParameterSource("requestId", requestId),
                new SubRequestRowMapper());
    }

    /**
     * The <code>SubRequestRowMapper</code> class represents mapper for {@link SubRequest} object.
     */
    private static final class SubRequestRowMapper implements RowMapper<SubRequest> {
        /**
         * Mapping data in {@link ResultSet} to a {@link SubRequest} entity.
         */
        @Override
        public SubRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            SubRequest subRequest = new SubRequest();
            subRequest.setId(rs.getLong("id"));
            subRequest.setRequestId(rs.getLong("request_id"));
            subRequest.setTitle(rs.getString("title"));
            subRequest.setDescription(rs.getString("description"));
            return subRequest;
        }
    }
}
