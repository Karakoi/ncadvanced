package com.overseer.dao.impl;

import com.overseer.dao.RequestDao;
import com.overseer.dao.UserDao;
import com.overseer.model.*;
import com.overseer.model.enums.ProgressStatus;
import lombok.Value;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RequestDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RequestDao requestDao;

    private Request request;
    private User assignee;
    private User reporter;
    private User lastChanger;
    private ProgressStatus progress;
    private PriorityStatus priority;
    private List<Long> requestsGroupIds;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value
    private static final class AuthParams {
        private final String email;
        private final String password;

        UsernamePasswordAuthenticationToken toAuthenticationToken() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

    @Before
    public void setUp() throws Exception {
        requestsGroupIds = Arrays.asList(114L, 115L, 116L);

        Role reporterRole = new Role("employee");
        reporterRole.setId(12L);
        reporter = new User();
        reporter.setFirstName("Mark");
        reporter.setLastName("Biggles");
        reporter.setPassword("securepass22");
        reporter.setEmail("valid@email.com");
        reporter.setRole(reporterRole);
        reporter = this.userDao.save(reporter);

        Role assigneeRole = new Role("office manager");
        assigneeRole.setId(11L);
        assignee = new User();
        assignee.setFirstName("Gavin");
        assignee.setLastName("Clarks");
        assignee.setPassword("rondo1890_");
        assignee.setEmail("blessed@email.com");
        assignee.setRole(assigneeRole);
        assignee = this.userDao.save(assignee);

        Role changerRole = new Role("admin");
        changerRole.setId(10L);
        lastChanger = new User();
        lastChanger.setFirstName("Bruce");
        lastChanger.setLastName("li");
        lastChanger.setPassword("qwerty123");
        lastChanger.setEmail("bruceli@email.com");
        lastChanger.setRole(changerRole);
        lastChanger = this.userDao.save(lastChanger);

        AuthParams params = new AuthParams(lastChanger.getEmail(), "qwerty123");
        UsernamePasswordAuthenticationToken loginToken = params.toAuthenticationToken();
        Authentication authentication = authenticationManager.authenticate(loginToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        priority = new PriorityStatus("Normal", 200);
        priority.setId(2L);

        progress = ProgressStatus.FREE;

        request = new Request();
        request.setTitle("Repair washing machine");
        request.setDescription("Do some busy work");
        request.setParentId(null);
        request.setEstimateTimeInDays(3);
        request.setDateOfCreation(LocalDateTime.of(2017, 6, 21, 12, 30));
        request.setReporter(reporter);
        request.setAssignee(assignee);
        request.setLastChanger(lastChanger);
        request.setPriorityStatus(priority);
        request.setProgressStatus(progress);

        request = requestDao.save(request);
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
        PriorityStatus high = new PriorityStatus("High", 300);
        high.setId(1L);
        request.setPriorityStatus(high);

        request.setProgressStatus(ProgressStatus.IN_PROGRESS);

        // when
        Request savedRequest = requestDao.save(request);

        // then
        assertThat(savedRequest, is(notNullValue()));
        assertThat(savedRequest.getPriorityStatus(), is(high));
        assertThat(savedRequest.getProgressStatus(), is(ProgressStatus.IN_PROGRESS));
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

    @Test
    public void shouldReturnTrueIfRequestExists() throws Exception {
        // given

        // wen
        boolean exists = requestDao.exists(request.getId());

        // then
        assertThat(exists, is(true));
    }

    @Test
    public void findRequestsByIds() throws Exception {
        List<Request> foundRequests = requestDao.findRequestsByIds(requestsGroupIds);

        Assert.assertNotNull(foundRequests);
        Assert.assertEquals(foundRequests.size(), requestsGroupIds.size());
    }
}