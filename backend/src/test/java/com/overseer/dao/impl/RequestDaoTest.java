package com.overseer.dao.impl;

import com.overseer.dao.RequestDao;
import com.overseer.dao.UserDao;
import com.overseer.model.PriorityStatus;
import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import com.overseer.model.Role;
import com.overseer.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RequestDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RequestDao requestDao;

    private Request request;
    private User assignee;
    private User reporter;
    private ProgressStatus progress;
    private PriorityStatus priority;

    @Before
    public void setUp() throws Exception {
        Role reporterRole = new Role("employee");
        reporterRole.setId(12L);
        reporter = new User("Mark", "Biggles", "securepass22", "valid@email.com", reporterRole);
        reporter = this.userDao.save(reporter);

        Role assigneeRole = new Role("office manager");
        reporterRole.setId(11L);
        assignee = new User("Gavin", "Clarks", "rondo1890_", "blessed@email.com", assigneeRole);
        assignee = this.userDao.save(assignee);

        priority = new PriorityStatus("Normal");
        priority.setId(2L);

        progress = new ProgressStatus("Free");
        progress.setId(5L);

        request = new Request();
        request.setTitle("Repair washing machine");
        request.setDescription("Do some busy work");
        request.setParentId(null);
        request.setEstimateTimeInDays(3);
        request.setDateOfCreation(LocalDateTime.of(2017, 6, 21, 12, 30));
        request.setReporter(reporter);
        request.setAssignee(assignee);
        request.setPriorityStatus(priority);
        request.setProgressStatus(progress);

        this.requestDao.save(request);
    }

    @Test
    public void shouldSaveRequest() throws Exception {
        // given

        // when
        Request savedRequest = requestDao.findOne(request.getId());

        // then
        assertThat(savedRequest, is(notNullValue()));
    }

    @Test
    public void shouldUpdateRequest() throws Exception {
        // given
        PriorityStatus high = new PriorityStatus("High");
        high.setId(1L);
        request.setPriorityStatus(high);

        ProgressStatus inProgress = new ProgressStatus("In progress");
        inProgress.setId(7L);
        request.setProgressStatus(inProgress);

        // when
        Request savedRequest = requestDao.save(request);

        // then
        assertThat(savedRequest, is(notNullValue()));
        assertThat(savedRequest.getPriorityStatus(), is(high));
        assertThat(savedRequest.getProgressStatus(), is(inProgress));
    }

    @Test
    public void shouldDeleteRequest() throws Exception {
        // given

        // when
        requestDao.delete(request);
        Request savedRequest = requestDao.findOne(request.getId());

        // then
        assertThat(savedRequest, is(nullValue()));
    }

    /*@Test
    public void shouldFindRequestByAssignee() throws Exception {
        // given

        // when
        List<Request> requestsByAssignee = requestDao.findRequestsByAssignee(assignee);

        // then
        assertThat(requestsByAssignee, is(notNullValue()));
        assertThat(requestsByAssignee, is(not(empty())));
    }

    @Test
    public void shouldFindRequestByReporter() throws Exception {
        // given

        // when
        List<Request> requestsByReporter = requestDao.findRequestsByReporter(reporter);

        // then
        assertThat(requestsByReporter, is(notNullValue()));
        assertThat(requestsByReporter, is(not(empty())));
    }*/
}