package com.overseer.dao.impl;

import com.overseer.dao.RequestDao;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
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
import java.util.Locale;

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
            "SELECT * FROM request r WHERE r.progress_status =: progressStatus";

    private static final String SELECT_REQUEST_BY_JOINED_REQUEST_ID =
            "SELECT * FROM request r WHERE r.joined_request_id =: joinedRequestId";

    private static final String SELECT_REQUEST_BY_REPORTER_ID =
            "SELECT * FROM request r WHERE r.reporter_id =: reporterId";

    private static final String SELECT_REQUEST_BY_ASSIGNEE_ID =
            "SELECT * FROM request r WHERE r.assignee_id =: assigneeId";

    private static final String SELECT_REQUEST_BY_PERIOD =
            "SELECT * FROM request r WHERE r.date_of_creation BETWEEN :begin AND :end";

    private static final String SELECT_REQUEST_BY_PRIORITY_STATUS =
            "SELECT * FROM request r WHERE r.priority_status =: priorityStatus";

    private static final String SELECT_REQUEST_BY_DATE = "SELECT * FROM request r WHERE r.date_of_creation =: date";

    private static final String SELECT_REQUEST_BY_ID = "SELECT * FROM request r WHERE r.id = :id";

    private static final String SELECT_ALL_REQUESTS = "SELECT * FROM request";

    private static final String DELETE_REQUEST_BY_ID = "DELETE FROM request r WHERE r.id = :id";

    private static final String UPDATE_REQUEST_BY_ID = "UPDATE request SET"
            + " title =: title,"
            + " description = :description,"
            + " priority_status = :priorityStatus,"
            + " progress_status = :progressStatus,"
            + " joined_request_id =: joinedRequestId,"
            + " reporter_id = :reporterId,"
            + " assignee_id = :assigneeId,"
            + " estimate_time_in_days = :estimateTimeInDays,"
            + " date_of_creation = :dateOfCreation"
            + " WHERE id = :id";

    private static final String INSERT_REQUEST = "INSERT INTO request"
            + " (title, description, priority_status, progress_status, joined_request_id,"
            + " reporter_id, assignee_id, estimate_time_in_days, date_of_creation)"
            + " VALUES"
            + " (:title, :description, :priorityStatus, :progressStatus, :joinedRequestId,"
            + " :reporterId, :assigneeId, :estimateTimeInDays, :dateOfCreation)";

    private final NamedParameterJdbcOperations jdbc;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Request save(Request request) {
        Assert.notNull(request);
        Long id = null;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("title", request.getTitle());
        mapSqlParameterSource.addValue("description", request.getDescription());
        mapSqlParameterSource.addValue("dateOfCreation", request.getDateOfCreation());
        mapSqlParameterSource.addValue("priorityStatus", request.getPriorityStatus().name());
        mapSqlParameterSource.addValue("progressStatus", request.getProgressStatus().name());
        mapSqlParameterSource.addValue("reporterId", request.getReporterId());
        mapSqlParameterSource.addValue("assigneeId", request.getAssigneeId());
        mapSqlParameterSource.addValue("estimateTimeInDays", request.getEstimateTimeInDays());
        mapSqlParameterSource.addValue("joinedRequestId", request.getJoinedRequestId());
        if (findOne(request.getId()) != null) {
            id = request.getId();
            mapSqlParameterSource.addValue("id", id);
            jdbc.update(UPDATE_REQUEST_BY_ID, mapSqlParameterSource);
        } else {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_REQUEST, mapSqlParameterSource, keyHolder);
            id = keyHolder.getKey().longValue();
        }
        request.setId(id);
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
                new MapSqlParameterSource("progressStatus", progressStatus.name()),
                new RequestMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Request> getRequestsByJoined(Long joinedRequestId) {
        Assert.notNull(joinedRequestId);
        return jdbc.query(SELECT_REQUEST_BY_JOINED_REQUEST_ID,
                new MapSqlParameterSource("joinedRequestId", joinedRequestId),
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
                new MapSqlParameterSource("priorityStatus", priorityStatus.name()),
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
            Request request = new Request(resultSet.getDate("date_of_creation").toLocalDate(),
                    PriorityStatus.valueOf(resultSet.getString("priority_status").toUpperCase(Locale.ENGLISH)),
                    ProgressStatus.valueOf(resultSet.getString("progress_status").toUpperCase(Locale.ENGLISH)),
                    resultSet.getLong("reporter_id"));
            request.setId(resultSet.getLong("id"));
            request.setTitle(resultSet.getString("title"));
            request.setDescription(resultSet.getString("description"));
            request.setJoinedRequestId(resultSet.getLong("joined_request_id"));
            request.setAssigneeId(resultSet.getLong("assignee_id"));
            request.setEstimateTimeInDays(resultSet.getInt("estimate_time_in_days"));

            return request;
        }
    }
}
