package com.overseer.dao.impl;

import static com.overseer.util.DeadlineCalculator.getDeadline;

import com.overseer.auth.service.SecurityContextService;
import com.overseer.dao.RequestDao;
import com.overseer.dto.DeadlineDTO;
import com.overseer.dto.RequestDTO;
import com.overseer.model.PriorityStatus;
import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.util.ProgressStatusUtil;
import lombok.val;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * <p>
 * Implementation of {@link RequestDao} interface.
 * </p>
 */
@Repository
public class RequestDaoImpl extends CrudDaoImpl<Request> implements RequestDao {


    private static final int HIGH = 1;
    private static final int NORMAL = 2;
    private static final int LOW = 3;
    private static final int DEFAULT_DAY_IN_MONTH = 1;
    private static final Long DEFAULT_MONTHS_STEP = 1L;
    private static final Long VALUE_TO_GET_STATISTIC_FOR_ALL_TIME = 10L;
    private static final int START_PROJECT_YEARS = 2017;
    private static final int START_PROJECT_MONTHS = 2;
    private static final int START_PROJECT_DAY = 7;

    private ProgressStatusUtil progressStatusUtil;
    private SecurityContextService securityContextService;

    public RequestDaoImpl(ProgressStatusUtil progressStatusUtil, SecurityContextService securityContextService) {
        this.progressStatusUtil = progressStatusUtil;
        this.securityContextService = securityContextService;
    }


    //-----------------------CRUD---------------------------

    @Override
    public Request save(Request request) {
        request.setLastChanger(securityContextService.currentUser());
        return super.save(request);
    }

    //-----------------------FIND---------------------------

    @Override
    public List<Request> findRequestsByReporterAndProgress(Long reporterId, ProgressStatus progress, int pageSize, int pageNumber) {
        Assert.notNull(reporterId, "id must not be null");
        try {
            val parameterSource = new MapSqlParameterSource("limit", pageSize);
            parameterSource.addValue("offset", pageSize * (pageNumber - 1));
            parameterSource.addValue("reporterId", reporterId);
            parameterSource.addValue("progress", progress.getId());
            return jdbc().query(queryService().getQuery("request.select").concat(
                    queryService().getQuery("request.findByReporterAndProgress")),
                    parameterSource,
                    this.getMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByProgressStatusesAndAssigneeId(List<Long> statusIds, Long assigneeId) {
        Assert.notNull(assigneeId, "id must not be null");
        Assert.notNull(statusIds, "list status ids must not be null");
        String findRequestsByProgressStatusAndAssigneeIdQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findRequestsByProgressStatusAndAssigneeId"));
        try {
            val parameterSource = new MapSqlParameterSource("assigneeId", assigneeId);
            parameterSource.addValue("progress_status_ids", statusIds);
            return jdbc().query(findRequestsByProgressStatusAndAssigneeIdQuery,
                    parameterSource,
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByProgressStatusesAndReporterId(List<Long> statusIds, Long reporterId) {
        Assert.notNull(reporterId, "id must not be null");
        Assert.notNull(statusIds, "list status ids must not be null");
        String findRequestsByProgressStatusAndReporterIdQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findRequestsByProgressStatusAndReporterId"));
        try {
            val parameterSource = new MapSqlParameterSource("reporterId", reporterId);
            parameterSource.addValue("progress_status_ids", statusIds);
            return jdbc().query(findRequestsByProgressStatusAndReporterIdQuery,
                    parameterSource,
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

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
        String joinedRequestsQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findJoinedRequests"));
        return jdbc().query(joinedRequestsQuery,
                new MapSqlParameterSource("id", id),
                this.getMapper());
    }

    @Override
    public List<Request> findRequestsByAssignee(Long assigneeId, int pageSize, int pageNumber) {
        Assert.notNull(assigneeId, "id must not be null");
        String findByAssigneeQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findByAssignee"));
        try {
            val parameterSource = new MapSqlParameterSource("limit", pageSize);
            parameterSource.addValue("offset", pageSize * (pageNumber - 1));
            parameterSource.addValue("assigneeId", assigneeId);
            return jdbc().query(findByAssigneeQuery,
                    parameterSource,
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsWithGivenProgressByAssignee(Long assigneeId, ProgressStatus progressStatus, int pageSize, int pageNumber) {
        Assert.notNull(assigneeId, "id must not be null");
        String findByAssigneeQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findRequestsWithGivenProgressByAssignee"));
        try {
            val parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("limit", pageSize);
            parameterSource.addValue("offset", pageSize * (pageNumber - 1));
            parameterSource.addValue("assigneeId", assigneeId);
            parameterSource.addValue("progressId", progressStatus.getId());
            return jdbc().query(findByAssigneeQuery, parameterSource, this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByReporter(Long reporterId, int pageSize, int pageNumber) {
        Assert.notNull(reporterId, "id must not be null");
        String findByReporterQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findByReporter"));
        try {
            val parameterSource = new MapSqlParameterSource("limit", pageSize);
            parameterSource.addValue("offset", pageSize * (pageNumber - 1));
            parameterSource.addValue("reporterId", reporterId);
            return jdbc().query(findByReporterQuery,
                    parameterSource,
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByPriority(Long priorityId, int pageSize, int pageNumber) {
        Assert.notNull(priorityId, "id must not be null");
        String findByPriorityQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findByPriority"));
        try {
            val parameterSource = new MapSqlParameterSource("limit", pageSize);
            parameterSource.addValue("offset", pageSize * (pageNumber - 1));
            parameterSource.addValue("priority_status_id", priorityId);
            return jdbc().query(findByPriorityQuery,
                    parameterSource,
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByPeriod(LocalDate begin, LocalDate end, int pageSize, int pageNumber) {
        Assert.notNull(begin, "begin time must not be null");
        Assert.notNull(end, "end time must not be null");
        String findByPeriodQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findByPeriod"));
        try {
            val parameterSource = new MapSqlParameterSource("limit", pageSize);
            parameterSource.addValue("offset", pageSize * (pageNumber - 1));
            parameterSource.addValue("begin", java.sql.Date.valueOf(begin));
            parameterSource.addValue("end", java.sql.Date.valueOf(end));
            return jdbc().query(findByPeriodQuery,
                    parameterSource,
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> findRequestsByDate(LocalDate date) {
        Assert.notNull(date, "Date must be not null");
        return jdbc().query(queryService().getQuery("request.select")
                        .concat(queryService().getQuery("request.byDate")),
                new MapSqlParameterSource("date", date),
                getMapper());
    }

    @Override
    public List<Request> findRequestsByIds(List<Long> ids) {
        Assert.notNull(ids, "ids must not be null");
        String subRequestsQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findRequestsByIds"));
        return jdbc().query(subRequestsQuery,
                new MapSqlParameterSource("ids", ids),
                this.getMapper());
    }

    @Override
    public List<Request> findFreeRequests(int pageSize, int pageNumber) {
        String findByStatusQuery = this.queryService().getQuery("request.select")
                .concat(queryService().getQuery("request.findFree"));
        try {
            val parameterSource = new MapSqlParameterSource("limit", pageSize);
            parameterSource.addValue("offset", pageSize * (pageNumber - 1));
            return jdbc().query(findByStatusQuery,
                    parameterSource,
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    //-----------------------COUNT---------------------------

    @Override
    public Long countRequestsWithNullParentByReporterAndProgress(Long reporterId, ProgressStatus progress) {
        val parameterSource = new MapSqlParameterSource("reporterId", reporterId);
        parameterSource.addValue("progress_id", progress.getId());
        return jdbc().queryForObject(queryService().getQuery("request.countByReporterAndProgress"),
                parameterSource, Long.class);
    }

    @Override
    public Long countRequestsByReporter(Long reporterId) {
        return jdbc().queryForObject(queryService().getQuery("request.countByReporter"),
                new MapSqlParameterSource("reporterId", reporterId), Long.class);
    }

    @Override
    public Long countRequestsByAssignee(Long managerId) {
        return jdbc().queryForObject(queryService().getQuery("request.countByAssignee"),
                new MapSqlParameterSource("assigneeId", managerId), Long.class);
    }

    @Override
    public Long countRequestsWithGivenProgressByAssignee(Long assigneeId, ProgressStatus progressStatus) {
        val parameterSource = new MapSqlParameterSource("assigneeId", assigneeId);
        parameterSource.addValue("progressStatusId", progressStatus.getId());
        return jdbc().queryForObject(queryService().getQuery("request.countRequestsWithGivenProgressByAssignee"),
                parameterSource, Long.class);
    }

    @Override
    public List<Long> countRequestByProgressStatus() {
        String quantityQuery = queryService().getQuery("request.countByProgressStatus");
        List<Long> progressList = new LinkedList<>();
        progressList.add(jdbc().queryForObject(quantityQuery, new MapSqlParameterSource("progress", ProgressStatus.FREE.getId()), Long.class));
        progressList.add(jdbc().queryForObject(quantityQuery, new MapSqlParameterSource("progress", ProgressStatus.JOINED.getId()), Long.class));
        progressList.add(jdbc().queryForObject(quantityQuery, new MapSqlParameterSource("progress", ProgressStatus.IN_PROGRESS.getId()), Long.class));
        return progressList;
    }

    @Override
    public List<Long> countRequestByProgressStatusForUser(Long userId) {
        List<Long> progressListForUser = new LinkedList<>();
        progressListForUser.add(countRequestByProgressStatusForReporter(userId, ProgressStatus.FREE));
        progressListForUser.add(countRequestByProgressStatusForReporter(userId, ProgressStatus.JOINED));
        progressListForUser.add(countRequestByProgressStatusForReporter(userId, ProgressStatus.IN_PROGRESS));

        return progressListForUser;
    }

    /**
     * Counts Request with given ProgressStatus for Reporter.
     *
     * @param reporterId reporter id
     * @param progressStatus specified progressStatus
     */
    private Long countRequestByProgressStatusForReporter(Long reporterId, ProgressStatus progressStatus) {
        String quantityQueryForUser = queryService().getQuery("request.countByProgressStatusForUser");
        val parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userId", reporterId);
        parameterSource.addValue("progress", progressStatus.getId());
        return jdbc().queryForObject(quantityQueryForUser, parameterSource, Long.class);
    }

    @Override
    public Long countTotalUsers() {
        val query = queryService().getQuery("user.total");
        return jdbc().queryForObject(query, new MapSqlParameterSource(), Long.class);
    }

    @Override
    public Long countTotalRequests() {
        String query = queryService().getQuery("request.total");
        return jdbc().queryForObject(query, new MapSqlParameterSource(), Long.class);
    }

    @Override
    public Long countRequestsCreatedToday() {
        String query = queryService().getQuery("request.today");
        return jdbc().queryForObject(query, new MapSqlParameterSource(), Long.class);
    }

    @Override
    public Long countRequestsRunningToday() {
        String request = queryService().getQuery("request.runningToday");
        return jdbc().queryForObject(request, new MapSqlParameterSource(), Long.class);
    }

    @Override
    public List<Long> countOpenClosedRequestForUser(Long userId, Long howLong) {
        LocalDate localDate = LocalDate.now().minusMonths(howLong);
        if (VALUE_TO_GET_STATISTIC_FOR_ALL_TIME.equals(howLong)) {
            localDate = LocalDate.of(START_PROJECT_YEARS, START_PROJECT_MONTHS, START_PROJECT_DAY);
        }
        String quantityQuery = queryService().getQuery("request.countStatisticForForUser");
        List<Long> userStatistic = new LinkedList<>();
        val parameterSource = new MapSqlParameterSource("userId", userId);
        parameterSource.addValue("howLong", localDate);
        parameterSource.addValue("userId", userId);
        userStatistic.add(jdbc().queryForObject(quantityQuery, parameterSource, Long.class));
        val source = new MapSqlParameterSource();
        source.addValue("howLong", localDate);
        source.addValue("progress", ProgressStatus.CLOSED.getId());
        source.addValue("userId", userId);
        String quantityQueryClosed = queryService().getQuery("request.countStatisticForUserClosed");
        userStatistic.add(jdbc().queryForObject(quantityQueryClosed, source, Long.class));
        return userStatistic;
    }

    @Override
    public List<Long> statisticForAdminDashBoard(Long howLong) {
        LocalDate localDate = LocalDate.now().minusMonths(howLong);
        if (VALUE_TO_GET_STATISTIC_FOR_ALL_TIME.equals(howLong)) {
            localDate = LocalDate.of(START_PROJECT_YEARS, START_PROJECT_MONTHS, START_PROJECT_DAY);
        }
        List<Long> adminStatisticList = new LinkedList<>();
        adminStatisticList.add(countStatisticForAdminDashBoardByProgressStatus(localDate, ProgressStatus.FREE));
        adminStatisticList.add(countStatisticForAdminDashBoardByProgressStatus(localDate, ProgressStatus.JOINED));
        adminStatisticList.add(countStatisticForAdminDashBoardByProgressStatus(localDate, ProgressStatus.IN_PROGRESS));
        adminStatisticList.add(countStatisticForAdminDashBoardByProgressStatus(localDate, ProgressStatus.CLOSED));

        String queryNotClosedRequests = queryService().getQuery("request.countStatisticForAdminDashBoardNotClosed");
        adminStatisticList.add(jdbc().queryForObject(queryNotClosedRequests, new MapSqlParameterSource("howLong", localDate), Long.class));

        adminStatisticList.add(countStatisticForAdminDashBoardByPriorityStatus(localDate, HIGH));
        adminStatisticList.add(countStatisticForAdminDashBoardByPriorityStatus(localDate, NORMAL));
        adminStatisticList.add(countStatisticForAdminDashBoardByPriorityStatus(localDate, LOW));

        return adminStatisticList;
    }

    /**
     * Counts Request with given ProgressStatus and Date.
     *
     * @param localDate date
     * @param progressStatus specified progressStatus
     */
    private Long countStatisticForAdminDashBoardByProgressStatus(LocalDate localDate, ProgressStatus progressStatus) {
        String queryProgress = queryService().getQuery("request.countStatisticForAdminDashBoardByProgressStatus");
        val parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("howLong", localDate);
        parameterSource.addValue("progress", progressStatus.getId());
        return  jdbc().queryForObject(queryProgress, parameterSource, Long.class);
    }

    /**
     * Counts Request with given PriorityStatus and Date.
     *
     * @param localDate date
     * @param priority specified progressStatus
     */
    private Long countStatisticForAdminDashBoardByPriorityStatus(LocalDate localDate, int priority) {
        String query = queryService().getQuery("request.countStatisticForAdminDashBoardByPriorityStatus");
        val parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("howLong", localDate);
        parameterSource.addValue("priority", priority);
        return  jdbc().queryForObject(query, parameterSource, Long.class);
    }

    @Override
    public List<Long> countRequestByPriorityStatus() {
        String quantityQuery = queryService().getQuery("request.countByPriorityStatus");
        List<Long> priorityList = new LinkedList<>();
        priorityList.add(jdbc().queryForObject(quantityQuery, new MapSqlParameterSource("priority", HIGH), Long.class));
        priorityList.add(jdbc().queryForObject(quantityQuery, new MapSqlParameterSource("priority", NORMAL), Long.class));
        priorityList.add(jdbc().queryForObject(quantityQuery, new MapSqlParameterSource("priority", LOW), Long.class));
        return priorityList;
    }

    @Override
    public Long countFree() {
        String findCountQuery = queryService().getQuery("request.countFree");
        return jdbc().queryForObject(findCountQuery, new MapSqlParameterSource(), Long.class);
    }

    @Override
    public Long countByAssignee(Long assigneeId) {
        String findCountQuery = queryService().getQuery("request.countByAssignee");
        return jdbc().queryForObject(findCountQuery, new MapSqlParameterSource("assigneeId", assigneeId), Long.class);
    }

    //-----------------------REPORT---------------------------

    @Override
    public RequestDTO findCountRequestsByPeriod(LocalDate start, LocalDate end, Long progressStatusId) {
        String findByPeriodQuery = this.queryService().getQuery("request.countByStatusAndPeriod");
        try {
            val parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("begin", java.sql.Date.valueOf(start));
            parameterSource.addValue("end", java.sql.Date.valueOf(end));
            parameterSource.addValue("progress_status_id", progressStatusId);
            return jdbc().queryForObject(findByPeriodQuery,
                    parameterSource, (resultSet, i) -> {
                        RequestDTO requestDTO = new RequestDTO();
                        requestDTO.setCount(resultSet.getLong("count"));
                        requestDTO.setStartDateLimit(start);
                        requestDTO.setEndDateLimit(end);
                        return requestDTO;
                    });
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<RequestDTO> findListCountRequestsByPeriod(LocalDate start, LocalDate end, Long progressStatusId) {
        String findCountByPeriodsQuery = this.queryService().getQuery("request.countByStatusesAndPeriods");
        List<RequestDTO> list = new ArrayList<>();
        try {
            val parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("begin", java.sql.Date.valueOf(start));
            parameterSource.addValue("end", java.sql.Date.valueOf(end));
            parameterSource.addValue("progress_status_id", progressStatusId);
            return jdbc().query(findCountByPeriodsQuery,
                    parameterSource, resultSet -> {
                        while (resultSet.next()) {
                            RequestDTO requestDTO = new RequestDTO();

                            int year = resultSet.getInt("year");
                            int month = resultSet.getInt("month");
                            LocalDate localStartDate = LocalDate.of(year, month, DEFAULT_DAY_IN_MONTH);
                            requestDTO.setStartDateLimit(localStartDate);

                            LocalDate localEndDate = LocalDate.of(year, month, DEFAULT_DAY_IN_MONTH);
                            localEndDate = localEndDate.plusMonths(DEFAULT_MONTHS_STEP);
                            requestDTO.setEndDateLimit(localEndDate);

                            requestDTO.setCount(resultSet.getLong("count"));
                            list.add(requestDTO);
                        }
                        return list;
                    });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RequestDTO findCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, Long progressStatusId, int id) {
        String findCountByManagerAndPeriodQuery = this.queryService().getQuery("request.countByManagerAndPeriod");
        try {
            val parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("begin", java.sql.Date.valueOf(start));
            parameterSource.addValue("end", java.sql.Date.valueOf(end));
            parameterSource.addValue("progress_status_id", progressStatusId);
            parameterSource.addValue("assignee_id", id);
            return jdbc().queryForObject(findCountByManagerAndPeriodQuery,
                    parameterSource, (resultSet, i) -> {
                        RequestDTO request = new RequestDTO();
                        request.setStartDateLimit(start);
                        request.setEndDateLimit(end);
                        request.setCount(resultSet.getLong("count"));
                        return request;
                    });
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<RequestDTO> findListCountRequestsByManagerAndPeriod(LocalDate start, LocalDate end, Long progressStatusId, int id) {
        List<RequestDTO> data = new ArrayList<>();
        try {
            val parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("begin", java.sql.Date.valueOf(start));
            parameterSource.addValue("end", java.sql.Date.valueOf(end));
            parameterSource.addValue("progress_status_id", progressStatusId);
            parameterSource.addValue("assignee_id", id);
            return jdbc().query(this.queryService().getQuery("request.countByManagerAndPeriods"),
                    parameterSource, resultSet -> {
                        while (resultSet.next()) {
                            RequestDTO request = new RequestDTO();
                            int year = resultSet.getInt("year");
                            int month = resultSet.getInt("month");
                            request.setCount(resultSet.getLong("count"));
                            LocalDate localStartDate = LocalDate.of(year, month, DEFAULT_DAY_IN_MONTH);
                            request.setStartDateLimit(localStartDate);

                            LocalDate localEndDate = LocalDate.of(year, month, DEFAULT_DAY_IN_MONTH);
                            localEndDate = localEndDate.plusMonths(DEFAULT_MONTHS_STEP);
                            request.setEndDateLimit(localEndDate);
                            System.out.println("request" + request);
                            data.add(request);
                        }
                        return data;
                    });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<RequestDTO> findListOfBestManagersByPeriod(LocalDate start, LocalDate end, Long progressStatusId, int countTop) {
        List<RequestDTO> bestManagers = new ArrayList<>();
        try {
            val parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("begin", java.sql.Date.valueOf(start));
            parameterSource.addValue("end", java.sql.Date.valueOf(end));
            parameterSource.addValue("progress_status_id", progressStatusId);
            parameterSource.addValue("top", countTop);
            return jdbc().query(this.queryService().getQuery("request.bestManagersByPeriod"),
                    parameterSource, resultSet -> {
                        while (resultSet.next()) {
                            RequestDTO manager = new RequestDTO();
                            manager.setCount(resultSet.getLong("count"));
                            manager.setManagerFirstName(resultSet.getString("first_name"));
                            manager.setManagerLastName(resultSet.getString("last_name"));
                            bestManagers.add(manager);
                        }
                        return bestManagers;
                    });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    //-----------------------LIFECYCLE---------------------------

    @Override
    public void deleteParentRequestIfItHasNoChildren(Long parentId) {
        Assert.notNull(parentId, "id must not be null");
        String deleteQuery = this.queryService().getQuery("request.deleteParentRequestIfHasNoChildren");
        this.jdbc().update(deleteQuery, new MapSqlParameterSource("id", parentId));
    }

    //-----------------------DEADLINE---------------------------

    @Override
    public List<DeadlineDTO> getDeadlinesByAssignee(Long assigneeID) {
        String getDeadlinesByAssignee = this.queryService().getQuery("deadlines.getByAssignee");
        List<DeadlineDTO> managerDeadlines = new ArrayList<>();
        try {
            val parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("assignee_id", assigneeID);
            return jdbc().query(getDeadlinesByAssignee,
                    parameterSource, resultSet -> {
                        while (resultSet.next()) {
                            DeadlineDTO deadline = new DeadlineDTO();
                            deadline.setId(resultSet.getLong("id"));
                            deadline.setTitle(resultSet.getString("title"));
                            deadline.setDeadline(getDeadline(resultSet.getDate("date").toLocalDate(),
                                    resultSet.getByte("estimate")));
                            managerDeadlines.add(deadline);
                        }
                        return managerDeadlines;
                    });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Request> searchRequests(String searchQuery) {
        String findByStatusQuery = this.queryService().getQuery("request.select").concat(searchQuery);
        try {
            return jdbc().query(findByStatusQuery, this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    //-----------------------QUERY---------------------------

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
        return queryService().getQuery("request.select").concat(queryService().getQuery("request.fetchPage"));
    }

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("request.count");
    }

    //-----------------------MAPPER---------------------------

    @Override
    protected RowMapper<Request> getMapper() {
        return (resultSet, i) -> {
            User reporter = new User();
            reporter.setId(resultSet.getLong("reporter_id"));
            reporter.setFirstName(resultSet.getString("reporter_first_name"));
            reporter.setLastName(resultSet.getString("reporter_last_name"));
            reporter.setEmail(resultSet.getString("reporter_email"));

            User assignee = new User();
            assignee.setId(resultSet.getLong("assignee_id"));
            if (resultSet.getString("assignee_first_name") != null) {
                assignee.setFirstName(resultSet.getString("assignee_first_name"));
                assignee.setLastName(resultSet.getString("assignee_last_name"));
                assignee.setEmail(resultSet.getString("assignee_email"));
            }

            User lastChanger = new User();
            lastChanger.setId(resultSet.getLong("last_changer_id"));
            lastChanger.setFirstName(resultSet.getString("last_changer_first_name"));
            lastChanger.setLastName(resultSet.getString("last_changer_last_name"));

            PriorityStatus priorityStatus = new PriorityStatus();
            priorityStatus.setName(resultSet.getString("priority_name"));
            priorityStatus.setId(resultSet.getLong("priority_id"));
            priorityStatus.setValue(resultSet.getInt("priority_value"));

            ProgressStatus progressStatus;
            Long progressStatusId = resultSet.getLong("progress_id");
            if (progressStatusId == null) {
                progressStatus = progressStatusUtil.getProgressById(0L);
            } else {
                progressStatus = progressStatusUtil.getProgressById(progressStatusId);
            }


            Long parentId = resultSet.getLong("parent_id");
            if (parentId == 0) {
                parentId = null;
            }

            Request request = new Request();
            request.setId(resultSet.getLong("id"));
            request.setTitle(resultSet.getString("title"));
            request.setDescription(resultSet.getString("description"));
            request.setParentId(resultSet.getLong("parent_id"));
            request.setEstimateTimeInDays(resultSet.getInt("estimate_time_in_days"));
            request.setDateOfCreation(resultSet.getTimestamp("date_of_creation").toLocalDateTime());
            request.setReporter(reporter);
            request.setAssignee(assignee);
            request.setLastChanger(lastChanger);
            request.setPriorityStatus(priorityStatus);
            request.setParentId(parentId);
            request.setProgressStatus(progressStatus);
            return request;
        };
    }
}
