package com.overseer.dao.impl;

import com.overseer.dao.RequestDao;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Implementation of {@link RequestDao} interface.
 * </p>
 */
@Repository
@Transactional
@RequiredArgsConstructor
public class RequestDaoImpl implements RequestDao {

    private static final String SELECT_REQUEST_BY_PROGRESS_STATUS =
            "SELECT * FROM request r WHERE r.progress_status_id =: progressStatusId";

    private static final String SELECT_REQUEST_BY_PARENT_ID =
            "SELECT * FROM request r WHERE r.parent_id =: parentId";

    private static final String SELECT_REQUEST_BY_REPORTER_ID =
            "SELECT * FROM request r WHERE r.reporter_id =: reporterId";

    private static final String SELECT_REQUEST_BY_ASSIGNEE_ID =
            "SELECT * FROM request r WHERE r.assignee_id =: assigneeId";

    private static final String SELECT_REQUEST_BY_PERIOD =
            "SELECT * FROM request r WHERE r.date_of_creation BETWEEN :begin AND :end";

    private static final String SELECT_REQUEST_BY_PRIORITY_STATUS =
            "SELECT * FROM request r WHERE r.priority_status_id =: priorityStatusId";

    private static final String SELECT_REQUEST_BY_DATE = "SELECT * FROM request r WHERE r.date_of_creation =: date";

    private static final String SELECT_REQUEST_BY_ID = "SELECT * FROM request r WHERE r.id = :id";

    private static final String SELECT_ALL_REQUESTS = "SELECT * FROM request";

    private static final String DELETE_REQUEST_BY_ID = "DELETE FROM request r WHERE r.id = :id";

    private static final String UPDATE_REQUEST_BY_ID = "UPDATE request SET"
            + " title =: title,"
            + " description = :description,"
            + " priority_status_id = :priorityStatusId,"
            + " progress_status_id = :progressStatusId,"
            + " parent_id =: parentId,"
            + " reporter_id = :reporterId,"
            + " assignee_id = :assigneeId,"
            + " estimate_time_in_days = :estimateTimeInDays,"
            + " date_of_creation = :dateOfCreation"
            + " WHERE id = :id";

    private static final String INSERT_REQUEST = "INSERT INTO request"
            + " (title, description, priority_status_id, progress_status_id, parent_id,"
            + " reporter_id, assignee_id, estimate_time_in_days, date_of_creation)"
            + " VALUES"
            + " (:title, :description, :priorityStatusId, :progressStatusId, :parentId,"
            + " :reporterId, :assigneeId, :estimateTimeInDays, :dateOfCreation)";

    private final NamedParameterJdbcOperations jdbc;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request save(Request request) {
        Assert.notNull(request, "request must not be null");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(request);
        if (request.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_REQUEST, sqlParameterSource, keyHolder, new String[]{"id"});
            long generatedId = keyHolder.getKey().longValue();
            request.setId(generatedId);
        } else {
            jdbc.update(UPDATE_REQUEST_BY_ID, sqlParameterSource);
        }
        return request;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request findOne(Long id) {
        Assert.notNull(id);
        try {
            return jdbc.queryForObject(SELECT_REQUEST_BY_ID,
                    new MapSqlParameterSource("id", id),
                    new RequestMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Request request) {
        Assert.notNull(request);
        Long id = request.getId();
        delete(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        jdbc.update(DELETE_REQUEST_BY_ID,
                new MapSqlParameterSource("id", id));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        Assert.notNull(id);
        return findOne(id) != null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> findAll() {
        return jdbc.query(SELECT_ALL_REQUESTS, new RequestMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByStatus(ProgressStatus progressStatus) {
        Assert.notNull(progressStatus);
        return jdbc.query(SELECT_REQUEST_BY_PROGRESS_STATUS,
                new MapSqlParameterSource("progressStatusId", progressStatus.getId()),
                new RequestMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByParent(Long parentId) {
        Assert.notNull(parentId);
        return jdbc.query(SELECT_REQUEST_BY_PARENT_ID,
                new MapSqlParameterSource("parentId", parentId),
                new RequestMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByAssignee(Long assigneeId) {
        Assert.notNull(assigneeId);
        return jdbc.query(SELECT_REQUEST_BY_ASSIGNEE_ID,
                new MapSqlParameterSource("assigneeId", assigneeId),
                new RequestMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByReporter(Long reporterId) {
        Assert.notNull(reporterId);
        return jdbc.query(SELECT_REQUEST_BY_REPORTER_ID,
                new MapSqlParameterSource("reporterId", reporterId),
                new RequestMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByPeriod(LocalDate begin, LocalDate end) {
        Assert.notNull(begin);
        Assert.notNull(end);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("begin", begin, Types.DATE);
        mapSqlParameterSource.addValue("end", end, Types.DATE);
        return jdbc.query(SELECT_REQUEST_BY_PERIOD,
                mapSqlParameterSource,
                new RequestMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestByDate(LocalDate date) {
        Assert.notNull(date);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("date", date, Types.DATE);
        return jdbc.query(SELECT_REQUEST_BY_DATE,
                mapSqlParameterSource,
                new RequestMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByPriority(PriorityStatus priorityStatus) {
        Assert.notNull(priorityStatus);
        return jdbc.query(SELECT_REQUEST_BY_PRIORITY_STATUS,
                new MapSqlParameterSource("priorityStatusId", priorityStatus.getId()),
                new RequestMapper());
    }

    /**
     * The <code>RequestMapper</code> class represents mapper for {@link Request} object.
     */
    private static final class RequestMapper implements RowMapper<Request> {
        /**
         * Mapping data in {@link ResultSet} to a {@link Request} entity.
         */
        @Override
        public Request mapRow(ResultSet resultSet, int i) throws SQLException {
            Request request = new Request(
                    resultSet.getString("title"),
                    resultSet.getDate("date_of_creation").toLocalDate(),
                    resultSet.getLong("progress_status_id"),
                    resultSet.getLong("priority_status_id"),
                    resultSet.getLong("reporter_id"));

            request.setId(resultSet.getLong("id"));
            request.setTitle(resultSet.getString("title"));
            request.setDescription(resultSet.getString("description"));
            request.setParentId(resultSet.getLong("parent_id"));
            request.setAssigneeId(resultSet.getLong("assignee_id"));
            request.setEstimateTimeInDays(resultSet.getInt("estimate_time_in_days"));

            return request;
        }
    }
}
