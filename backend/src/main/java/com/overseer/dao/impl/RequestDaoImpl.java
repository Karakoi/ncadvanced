package com.overseer.dao.impl;

import com.overseer.dao.RequestDao;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
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
import java.sql.Timestamp;
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

    private static final String INSERT_OR_UPDATE_REQUEST =
            "INSERT INTO request (id, title, description, priority_status_id, progress_status_id,\n"
                    + "                     reporter_id, assignee_id, estimate_time_in_days, date_of_creation,\n"
                    + "                     parent_id)\n"
                    + "VALUES (:id, :title, :description, :priorityStatusId, :progressStatusId,\n"
                    + "        :reporterId, :assigneeId, :estimateTimeInDays, current_timestamp,\n"
                    + "        :parentId)\n"
                    + "ON CONFLICT (id)\n"
                    + "  DO UPDATE SET\n"
                    + "    title = :title,\n"
                    + "    description = :description,\n"
                    + "    priority_status_id = :priorityStatusId,\n"
                    + "    progress_status_id = :progressStatusId,\n"
                    + "    reporter_id = :reporterId,\n"
                    + "    assignee_id = :assigneeId,\n"
                    + "    estimate_time_in_days = :estimateTimeInDays,\n"
                    + "    date_of_creation = :creationDate,\n"
                    + "    parent_id = :parentId";

    private static final String SELECT =
            "SELECT"
                    + "  request.id                    id,\n"
                    + "  request.title                 title,\n"
                    + "  request.description           description,\n"
                    + "  request.parent_id             parent,\n"
                    + "  request.estimate_time_in_days estimate,\n"
                    + "  request.date_of_creation      creation,\n"
                    + "  priority_status.id            priority_id,\n"
                    + "  priority_status.name          priority,\n"
                    + "  progress_status.id            progress_id,\n"
                    + "  progress_status.name          progress,\n"
                    + "  reporter.id                   reporter_id,\n"
                    + "  reporter.first_name           reporter_firstname,\n"
                    + "  reporter.last_name            reporter_lastname,\n"
                    + "  assignee.id                   assignee_id,\n"
                    + "  assignee.first_name           assignee_firstname,\n"
                    + "  assignee.last_name            assignee_lastname\n"
                    + "FROM request\n"
                    + "  LEFT JOIN priority_status ON request.priority_status_id = priority_status.id\n"
                    + "  LEFT JOIN progress_status ON request.progress_status_id = progress_status.id\n"
                    + "  LEFT JOIN \"user\" AS reporter ON request.reporter_id = reporter.id\n"
                    + "  LEFT JOIN \"user\" AS assignee ON request.assignee_id = assignee.id\n"
                    + "WHERE ";

    private static final String SELECT_BY_ID = SELECT.concat("request.id = :id");

    private static final String DELETE_BY_ID = "DELETE FROM request WHERE id = :id";

    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM request WHERE id = :id";

    private static final String SELECT_REQUESTS_BY_PARENT = SELECT.concat("request.parent_id = :parentId");

    private static final String SELECT_REQUESTS_BY_ASSIGNEE = SELECT.concat("request.assignee_id = :assigneeId");

    private static final String SELECT_REQUESTS_BY_REPORTER = SELECT.concat("request.reporter_id = :reporterId");

    private static final String SELECT_REQUESTS_BY_PERIOD =
            SELECT.concat("request.date_of_creation BETWEEN :begin AND :end");

    private static final String SELECT_REQUESTS_BY_PROGRESS_STATUS =
            SELECT.concat("request.progress_status_id = :progress_status_id");

    private static final String SELECT_REQUESTS_BY_PRIORITY_STATUS =
            SELECT.concat("request.priority_status_id = :priority_status_id");

    private static final String SELECT_ALL_REQUESTS =
            SELECT.concat("request.priority_status_id NOTNULL AND request.progress_status_id NOTNULL");

    private static final String SELECT_SUBREQUESTS_BY_ID =
            SELECT.concat("request.progress_status_id IS NULL AND request.parent_id = :id");

    private static final String SELECT_ALL_JOINED =
            SELECT.concat("request.parent_id IS NULL AND request.progress_status_id != 6");

    private static final String SELECT_REQUESTS_BY_JOINED =
            SELECT.concat("request.parent_id = :id AND request.progress_status_id = 6");

    private final NamedParameterJdbcOperations jdbc;


    @Override
    public Request save(Request request) {
        Assert.notNull(request, "request must not be null");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", request.getId());
        mapSqlParameterSource.addValue("title", request.getTitle());
        mapSqlParameterSource.addValue("description", request.getDescription());
        mapSqlParameterSource.addValue("priorityStatusId", request.getPriorityStatus().getId());
        mapSqlParameterSource.addValue("progressStatusId", request.getProgressStatus().getId());
        mapSqlParameterSource.addValue("reporterId", request.getReporter().getId());
        mapSqlParameterSource.addValue("assigneeId", request.getAssignee().getId());
        mapSqlParameterSource.addValue("estimateTimeInDays", request.getEstimateTimeInDays());
        mapSqlParameterSource.addValue("parentId", request.getParentId());
        mapSqlParameterSource.addValue("creationDate", Timestamp.valueOf(request.getDateOfCreation()));

        if (request.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_OR_UPDATE_REQUEST, mapSqlParameterSource, keyHolder, new String[]{"id"});
            long generatedId = keyHolder.getKey().longValue();
            request.setId(generatedId);
        } else {
            jdbc.update(INSERT_OR_UPDATE_REQUEST, mapSqlParameterSource);
        }
        return request;
    }

    @Override
    public Request findOne(Long id) {
        Assert.notNull(id, "id must not be null");
        try {
            return jdbc.queryForObject(SELECT_BY_ID, new MapSqlParameterSource("id", id), new RequestMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Request entity) {
        Assert.notNull(entity, "id must not be null");
        delete(entity.getId());
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id, "id must not be null");
        jdbc.update(DELETE_BY_ID, new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean exists(Long id) {
        Assert.notNull(id, "id must not be null");
        return jdbc.queryForObject(EXISTS_BY_ID,
                new MapSqlParameterSource("id", id), Integer.class) > 0;
    }

    @Override
    public List<Request> findAll() {
        return jdbc.query(SELECT_ALL_REQUESTS, new RequestMapper());
    }

    @Override
    public List<Request> getJoinedGroups() {
        return jdbc.query(SELECT_ALL_JOINED, new RequestMapper());
    }

    @Override
    public List<Request> getJoinedGroupRequests(Long id) {
        Assert.notNull(id, "id must not be null");
        return jdbc.query(SELECT_REQUESTS_BY_JOINED, new MapSqlParameterSource("id", id), new RequestMapper());
    }

    @Override
    public List<Request> getSubRequests(Long id) {
        Assert.notNull(id, "id must not be null");
        return jdbc.query(SELECT_SUBREQUESTS_BY_ID, new MapSqlParameterSource("id", id), new RequestMapper());
    }

    @Override
    public List<Request> getRequestsByParent(Long parentId) {
        Assert.notNull(parentId, "id must not be null");
        try {
            return jdbc.query(SELECT_REQUESTS_BY_PARENT,
                    new MapSqlParameterSource("parentId", parentId),
                    new RequestMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> getRequestsByAssignee(Long assigneeId) {
        Assert.notNull(assigneeId, "id must not be null");
        try {
            return jdbc.query(SELECT_REQUESTS_BY_ASSIGNEE,
                    new MapSqlParameterSource("assigneeId", assigneeId),
                    new RequestMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> getRequestsByReporter(Long reporterId) {
        Assert.notNull(reporterId, "id must not be null");
        try {
            return jdbc.query(SELECT_REQUESTS_BY_REPORTER,
                    new MapSqlParameterSource("reporterId", reporterId),
                    new RequestMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> getRequestsByPeriod(LocalDate begin, LocalDate end) {
        Assert.notNull(begin, "begin time must not be null");
        Assert.notNull(end, "end time must not be null");
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("begin", java.sql.Date.valueOf(begin));
            mapSqlParameterSource.addValue("end", java.sql.Date.valueOf(end));
            return jdbc.query(SELECT_REQUESTS_BY_PERIOD,
                    mapSqlParameterSource,
                    new RequestMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> getRequestsByDate(LocalDate date) {
        return getRequestsByPeriod(date, date.plusDays(1));
    }

    @Override
    public List<Request> getRequestsByStatus(ProgressStatus progressStatus) {
        Assert.notNull(progressStatus, "id must not be null");
        try {
            return jdbc.query(SELECT_REQUESTS_BY_PROGRESS_STATUS,
                    new MapSqlParameterSource("progress_status_id", progressStatus.getId()),
                    new RequestMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> getRequestsByPriority(PriorityStatus priorityStatus) {
        Assert.notNull(priorityStatus, "id must not be null");
        try {
            return jdbc.query(SELECT_REQUESTS_BY_PRIORITY_STATUS,
                    new MapSqlParameterSource("priority_status_id", priorityStatus.getId()),
                    new RequestMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Request changeProgressStatus(Request request) {
        return save(request);
    }

    @Override
    public Request changePriorityStatus(Request request) {
        return save(request);
    }

    /**
     * Mapping data in {@link ResultSet} to a {@link Request} entity.
     */
    private static final class RequestMapper implements RowMapper<Request> {

        @Override
        public Request mapRow(ResultSet resultSet, int i) throws SQLException {
            User reporter = new User();
            reporter.setId(resultSet.getLong("reporter_id"));
            reporter.setFirstName(resultSet.getString("reporter_firstname"));
            reporter.setLastName(resultSet.getString("reporter_lastname"));

            User assignee = new User();
            assignee.setId(resultSet.getLong("assignee_id"));
            assignee.setFirstName(resultSet.getString("assignee_firstname"));
            assignee.setLastName(resultSet.getString("assignee_lastname"));

            ProgressStatus progressStatus = new ProgressStatus();
            progressStatus.setName(resultSet.getString("progress"));
            progressStatus.setId(resultSet.getLong("progress_id"));
            PriorityStatus priorityStatus = new PriorityStatus();
            priorityStatus.setName(resultSet.getString("priority"));
            priorityStatus.setId(resultSet.getLong("priority_id"));

            Request request = new Request();

            request.setId(resultSet.getLong("id"));
            request.setTitle(resultSet.getString("title"));
            request.setDescription(resultSet.getString("description"));
            request.setParentId(resultSet.getLong("parent"));
            request.setEstimateTimeInDays(resultSet.getInt("estimate"));
            request.setDateOfCreation(resultSet.getTimestamp("creation").toLocalDateTime());
            request.setReporter(reporter);
            request.setAssignee(assignee);
            request.setProgressStatus(progressStatus);
            request.setPriorityStatus(priorityStatus);

            return request;
        }
    }

}
