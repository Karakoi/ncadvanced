package com.overseer.dao.impl;

import com.overseer.dao.RequestDao;
import com.overseer.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by Romanova on 18.02.2017.
 */
public class RequestDaoImpl implements RequestDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     @{inheritDoc}
     */
    @Override
    public Request create(Request request){
        final String sql = "insert into request(" +
                "title, " +
                "description, " +
                "priority_status_id, " +
                "progress_status_id, " +
                "joined_request_id, " +
                "reporter_id, " +
                "assignee_id, " +
                "estimate_time_in_days, " +
                "history_detail_id, " +
                "previous_request_id, " +
                "date_of_creation) " +
                "values(?,?,?,?,?,?,?,?,?,?,?)";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.getTitle());
            ps.setString(2, request.getDescription());
            ps.setLong(3, request.getPriorityStatus().getId());
            ps.setLong(4, request.getProgressStatus().getId());
            ps.setLong(5, request.getJoinedRequestId());
            ps.setLong(6, request.getReporterId());
            ps.setLong(7, request.getAssigneeId());
            ps.setInt(8, request.getEstimateTimeInDays());
            ps.setLong(9, request.getHistoryDetailId());
            ps.setLong(10, request.getPreviousRequestId());
            ps.setDate(11, java.sql.Date.valueOf(request.getDateOfCreation()));
            return ps;
        }, holder);

        Long newRequestId = holder.getKey().longValue();
        request.setId(newRequestId);
        return request;
    }

    /**
     @{inheritDoc}
     */
    @Override
    public void update(Request request){
        String sql = "update request set " +
                "title = ?, " +
                "description = ?, " +
                "priority_status_id = ?, " +
                "progress_status_id = ?, " +
                "joined_request_id = ?, " +
                "reporter_id = ?, " +
                "assignee_id = ?, " +
                "estimate_time_in_days = ?, " +
                "history_detail_id = ?, " +
                "previous_request_id = ?, " +
                "date_of_creation = ? " +
                "where id = ?";
        this.jdbcTemplate.update(sql,
                request.getTitle(),
                request.getDescription(),
                request.getPriorityStatus().getId(),
                request.getProgressStatus().getId(),
                request.getJoinedRequestId(),
                request.getReporterId(),
                request.getAssigneeId(),
                request.getEstimateTimeInDays(),
                request.getHistoryDetailId(),
                request.getPreviousRequestId(),
                java.sql.Date.valueOf(request.getDateOfCreation()),
                request.getId());
    }

    /**
     @{inheritDoc}
     */
    @Override
    public void delete(Request request){
        this.jdbcTemplate.update(
                "delete from request where id = ?",
                request.getId());
    }
}
