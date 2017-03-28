package com.overseer.service.impl;

import com.overseer.dao.RequestDao;
import com.overseer.dao.UserDao;
import com.overseer.exception.InappropriateProgressStatusException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.*;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.RequestService;
import com.overseer.service.UserService;
import lombok.Value;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RequestServiceImplTest {



    @Autowired
    private UserDao userDao;
    @Autowired
    private RequestDao requestDao;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

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


        requestsGroupIds = Arrays.asList(212L, 181L, 123L);

        Role reporterRole = new Role("employee");
        reporterRole.setId(12L);
        reporter = new User();
        reporter.setFirstName("Tom");
        reporter.setLastName("Hardy");
        reporter.setPassword("gunner12");
        reporter.setEmail("tomy@gmail.com");
        reporter.setRole(reporterRole);

        reporter = this.userDao.save(reporter);


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

        Role assigneeRole = new Role("office manager");
        assigneeRole.setId(11L);
        assignee = new User();
        assignee.setFirstName("Tom");
        assignee.setLastName("Cruz");
        assignee.setPassword("cruzXXX");
        assignee.setEmail("blabla@3g.ua");
        assignee.setRole(assigneeRole);
        assignee = this.userDao.save(assignee);

        priority = new PriorityStatus("Normal", 200);
        priority.setId(2L);

        progress = ProgressStatus.FREE;

        request = new Request();
        request.setTitle("Do something");
        request.setDescription("Do some great work");
        request.setParentId(null);
        request.setEstimateTimeInDays(3);
        request.setDateOfCreation(LocalDateTime.of(2015, 6, 21, 12, 30));
        request.setReporter(reporter);
        request.setAssignee(assignee);
        request.setLastChanger(lastChanger);
        request.setPriorityStatus(priority);
        request.setProgressStatus(progress);


        this.requestDao.save(request);

    }

    @Ignore
    @Test
    public void joinRequestsIntoParent() throws Exception {
        Request parent = requestService.joinRequestsIntoParent(requestsGroupIds, request);

        Assert.assertEquals(parent.getId(), request.getId());

        Request firstChildRequest = requestService.findOne(requestsGroupIds.get(0));
        Assert.assertEquals(request.getId(), firstChildRequest.getParentId());

        Request secondChildRequest = requestService.findOne(requestsGroupIds.get(1));
        Assert.assertEquals(request.getId(), secondChildRequest.getParentId());

        Request thirdChildRequest = requestService.findOne(requestsGroupIds.get(2));
        Assert.assertEquals(request.getId(), thirdChildRequest.getParentId());
    }

    @Test
    public void shouldAssignFreeRequest() throws Exception {
        // given

        // when
        Request assignRequest = requestService.assignRequest(request);

        // then
        assertThat(assignRequest.getProgressStatus(), is(ProgressStatus.IN_PROGRESS));
    }

    @Test(expected=NoSuchEntityException.class)
    public void shouldNotReopenRequest() throws Exception {
        // given

        // when
        requestService.assignRequest(request);
        requestService.closeRequest(request);
        userService.delete(request.getReporter());
//        userDao.delete(request.getReporter());
        requestService.reopenRequest(request.getId());

        // then
    }

    @Test(expected=InappropriateProgressStatusException.class)
    public void shouldNotCloseFreeRequest() {
        // given

        // when
        requestService.closeRequest(request);

        // then
    }
}