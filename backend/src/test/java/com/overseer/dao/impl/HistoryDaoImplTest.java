package com.overseer.dao.impl;

import com.overseer.dao.HistoryDAO;
import com.overseer.dao.RequestDao;
import com.overseer.dao.UserDao;
import com.overseer.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 *  Test for {@link HistoryDAO}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class HistoryDaoImplTest {

    @Autowired
    private HistoryDAO historyDAO;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private UserDao userDao;

    private History history1;

    /*private static final String TEST_COLUMN_NAME = "title name of topic";
    private static final LocalDateTime TEST_DATE_OF_LAST_CHANGES = LocalDateTime.parse("2007-12-03T00:00:00");
    private static final User TEST_CHANGER = new User("firstName", "lastName", "pass", "123#gmail.com", new Role("admin"));
    private static final Long TEST_RECORD_ID = 9999999L;
    private static Topic topic = new Topic("Amazing topic");
    static {
        topic.setId(TEST_RECORD_ID);
    }*/

    private static final String TEST_TITLE_1 = "title 1";
    private static final String TEST_TITLE_2 = "title 2";
    private static final String TEST_DESCRIPTION_1 = "desc 1";
    private static final String TEST_DESCRIPTION_2 = "desc 2";

    private Request request;
    private User assignee;
    private User reporter;
    private ProgressStatus progress;
    private PriorityStatus priority;
    private List<Long> requestsGroupIds;

    @Before
    public void setUp() throws Exception{
        /*history1 = new History();
        history1.setColumnName(TEST_COLUMN_NAME);
        history1.setOldValue("old title 1");
        history1.setNewValue("new title 1");
        history1.setDateOfLastChange(TEST_DATE_OF_LAST_CHANGES);
        history1.setChanger(TEST_CHANGER);
        history1.setRecordId(topic.getId());

        history1 = historyDAO.save(history1);*/

        Role reporterRole = new Role("employee");
        reporterRole.setId(880L);
        reporter = new User("EmployeeName", "EmployeeSurname", "EmployeePassword", "employeeEmail@email.com", reporterRole);
        reporter = this.userDao.save(reporter);

        Role assigneeRole = new Role("admin");
        reporterRole.setId(885L);
        assignee = new User("AdminName", "AdminSurname", "AdminPassword", "adminEemail@email.com", assigneeRole);
        assignee = this.userDao.save(assignee);

        priority = new PriorityStatus("Normal", 200);
        priority.setId(890L);

        progress = new ProgressStatus("Free", 200);
        progress.setId(895L);

        request = new Request();
        request.setTitle(TEST_TITLE_1);
        request.setDescription(TEST_TITLE_2);
        request.setParentId(null);
        request.setEstimateTimeInDays(3);
        request.setDateOfCreation(LocalDateTime.of(2017, 6, 21, 12, 30));
        request.setReporter(reporter);
        request.setAssignee(assignee);
        request.setPriorityStatus(priority);
        request.setProgressStatus(progress);

        request = requestDao.save(request);
    }
/*
    @Test
    public void findAllForEntity() throws Exception {
        // given
        history1 = new History();
        history1.setColumnName("title");
        history1.setOldValue(TEST_TITLE_1);
        history1.setNewValue(TEST_TITLE_2);
        history1.setDateOfLastChange(TEST_DATE_OF_LAST_CHANGES);
        history1.setChanger(request.get);
        history1.setRecordId(topic.getId());

        request.setTitle(TEST_DESCRIPTION_1);
        request.setDescription(TEST_DESCRIPTION_2);

        // when
        requestDao.save(request);

        // then
        List<History> expectedList = Arrays.asList(history1, history2);
        assertThat(allHistoryForEntity, is(expectedList));
    }*/
}