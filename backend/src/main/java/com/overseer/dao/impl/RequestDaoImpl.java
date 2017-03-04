package com.overseer.dao.impl;

import com.overseer.dao.RequestDao;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Implementation of {@link RequestDao} interface.
 * </p>
 */
@Repository
public class RequestDaoImpl extends CrudDaoImpl<Request> implements RequestDao {

    @Override
    public List<Request> findSubRequests(Long id) {
        Assert.notNull(id, "id must not be null");
        String subRequestsQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findSubRequests"));
        return jdbc().query(subRequestsQuery,
                new MapSqlParameterSource("id", id),
                this.getMapper());
    }

    @Override
    public List<Request> findJoinedRequests(Long id) {
        Assert.notNull(id, "id must not be null");
        String subRequestsQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findJoinedRequests"));
        return jdbc().query(subRequestsQuery,
                new MapSqlParameterSource("id", id),
                this.getMapper());
    }

    @Override
    public List<Request> findRequestsByAssignee(Long assigneeId) {
        Assert.notNull(assigneeId, "id must not be null");
        String findByAssigneeQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findByAssignee"));
        try {
            return jdbc().query(findByAssigneeQuery,
                    new MapSqlParameterSource("assigneeId", assigneeId),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByReporter(Long reporterId) {
        Assert.notNull(reporterId, "id must not be null");
        String findByReporterQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findByReporter"));
        try {
            return jdbc().query(findByReporterQuery,
                    new MapSqlParameterSource("reporterId", reporterId),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByProgress(Long statusId) {
        Assert.notNull(statusId, "id must not be null");
        String findByStatusQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findByStatus"));
        try {
            return jdbc().query(findByStatusQuery,
                    new MapSqlParameterSource("progress_status_id", statusId),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByPriority(Long priorityId) {
        Assert.notNull(priorityId, "id must not be null");
        String findByPriorityQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findByPriority"));
        try {
            return jdbc().query(findByPriorityQuery,
                    new MapSqlParameterSource("priority_status_id", priorityId),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByPeriod(LocalDate begin, LocalDate end) {
        Assert.notNull(begin, "begin time must not be null");
        Assert.notNull(end, "end time must not be null");
        String findByPeriodQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findByPeriod"));
        try {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("begin", java.sql.Date.valueOf(begin));
            mapSqlParameterSource.addValue("end", java.sql.Date.valueOf(end));
            return jdbc().query(findByPeriodQuery,
                    mapSqlParameterSource,
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByDate(LocalDate date) {
        return findRequestsByPeriod(date, date.plusDays(1));
    }

    @Override
    protected String getInsertQuery() {
        return queryService().getQuery("request.insert");
    }

    @Override
    protected String getFindOneQuery() {
        return queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findOne"));
    }

    @Override
    protected String getDeleteQuery() {
        return queryService().getQuery("request.delete");
    }

    @Override
    protected String getExistsQuery() {
        return queryService().getQuery("request.exists");
    }

    @Override
    protected String getFindAllQuery() {
        return queryService().getQuery("request.fetchPage");
    }

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("request.count");
    }

    @Override
    protected RowMapper<Request> getMapper() {
        return (resultSet, i) -> {
            User reporter = new User();
            reporter.setId(resultSet.getLong("reporter_id"));
            reporter.setFirstName(resultSet.getString("reporter_first_name"));
            reporter.setLastName(resultSet.getString("reporter_last_name"));

            User assignee = new User();
            assignee.setId(resultSet.getLong("assignee_id"));
            assignee.setFirstName(resultSet.getString("assignee_first_name"));
            assignee.setLastName(resultSet.getString("assignee_last_name"));

            ProgressStatus progressStatus = new ProgressStatus();
            progressStatus.setName(resultSet.getString("progress_name"));
            progressStatus.setId(resultSet.getLong("progress_id"));

            PriorityStatus priorityStatus = new PriorityStatus();
            priorityStatus.setName(resultSet.getString("priority_name"));
            priorityStatus.setId(resultSet.getLong("priority_id"));

            Request request = new Request();
            request.setId(resultSet.getLong("id"));
            request.setTitle(resultSet.getString("title"));
            request.setDescription(resultSet.getString("description"));
            request.setParentId(resultSet.getLong("parent_id"));
            request.setEstimateTimeInDays(resultSet.getInt("estimate_time_in_days"));
            request.setDateOfCreation(resultSet.getTimestamp("date_of_creation").toLocalDateTime());
            request.setReporter(reporter);
            request.setAssignee(assignee);
            request.setPriorityStatus(priorityStatus);
            request.setProgressStatus(progressStatus);
            return request;
        };
    }
}
