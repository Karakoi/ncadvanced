package com.overseer;

import com.overseer.exception.InappropriateProgressStatusException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.*;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.RequestService;
import com.overseer.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RequestLifecycleTest {
    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Request request;
    private User assignee;
    private User reporter;
    private User lastChanger;
    private PriorityStatus priority;

    @Before
    public void setUp() throws Exception {
        Role reporterRole = new Role("employee");
        reporterRole.setId(12L);
        reporter = new User();
        reporter.setFirstName("Tom");
        reporter.setLastName("Hardy");
        reporter.setPassword("gunner12");
        reporter.setEmail("tomy@gmail.com");
        reporter.setRole(reporterRole);
        reporter = userService.create(reporter);

        Role assigneeRole = new Role("office manager");
        assigneeRole.setId(11L);
        assignee = new User();
        assignee.setFirstName("Tom");
        assignee.setLastName("Cruz");
        assignee.setPassword("cruzXXX");
        assignee.setEmail("blabla@3g.ua");
        assignee.setRole(assigneeRole);
        assignee = userService.create(assignee);

        Role changerRole = new Role("admin");
        changerRole.setId(10L);
        lastChanger = new User();
        lastChanger.setFirstName("Bruce");
        lastChanger.setLastName("li");
        lastChanger.setPassword("qwerty123");
        lastChanger.setEmail("brucelicka@email.com");
        lastChanger.setRole(changerRole);
        lastChanger = userService.create(lastChanger);

        UsernamePasswordAuthenticationToken loginToken =
                new UsernamePasswordAuthenticationToken(lastChanger.getEmail(), "qwerty123");
        Authentication authentication = authenticationManager.authenticate(loginToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        priority = new PriorityStatus("Normal", 200);
        priority.setId(2L);

        request = new Request();
        request.setTitle("Do something");
        request.setDescription("Do some great work");
        request.setDateOfCreation(LocalDateTime.of(2015, 6, 21, 12, 30));
        request.setReporter(reporter);
        request.setAssignee(new User());
        request.setPriorityStatus(priority);
        request.setProgressStatus(ProgressStatus.FREE);
        request = requestService.create(request);
    }

    @Test
    public void shouldJoinRequestsIntoParent() throws Exception {
        // given
        List<Long> requestsGroupIds = new ArrayList<>();

        Request requestFirstChild = new Request();
        requestFirstChild.setTitle("Do something very important");
        requestFirstChild.setDescription("It is hard job");
        requestFirstChild.setDateOfCreation(LocalDateTime.of(2015, 6, 21, 12, 30));
        requestFirstChild.setReporter(reporter);
        requestFirstChild.setAssignee(new User());
        requestFirstChild.setPriorityStatus(priority);
        requestFirstChild.setProgressStatus(ProgressStatus.FREE);
        requestFirstChild = requestService.create(requestFirstChild);

        Request requestSecondChild = new Request();
        requestSecondChild.setTitle("Do something really important");
        requestSecondChild.setDescription("Do hard work");
        requestSecondChild.setDateOfCreation(LocalDateTime.of(2015, 6, 21, 12, 30));
        requestSecondChild.setReporter(reporter);
        requestSecondChild.setAssignee(new User());
        requestSecondChild.setPriorityStatus(priority);
        requestSecondChild.setProgressStatus(ProgressStatus.FREE);
        requestSecondChild = requestService.create(requestSecondChild);

        requestsGroupIds.add(requestFirstChild.getId());
        requestsGroupIds.add(requestSecondChild.getId());

        request.setAssignee(assignee);
        request.setEstimateTimeInDays(3);
        requestService.update(request);

        //when
        requestService.joinRequestsIntoParent(requestsGroupIds, request);
        requestFirstChild = requestService.findOne(requestFirstChild.getId());
        requestSecondChild = requestService.findOne(requestSecondChild.getId());

        //then
        assertThat(requestFirstChild.getParentId(), is(request.getId()));
        assertThat(requestSecondChild.getParentId(), is(request.getId()));

        assertThat(requestFirstChild.getProgressStatus(), is(ProgressStatus.JOINED));
        assertThat(requestSecondChild.getProgressStatus(), is(ProgressStatus.JOINED));

        assertThat(request.getProgressStatus(), is(ProgressStatus.IN_PROGRESS));

    }

    @Test
    public void shouldAssignFreeRequest() throws Exception {
        // given
        request.setAssignee(assignee);
        request.setEstimateTimeInDays(3);
        requestService.update(request);

        // when
        Request assignRequest = requestService.assignRequest(request);

        // then
        assertThat(assignRequest.getProgressStatus(), is(ProgressStatus.IN_PROGRESS));
    }

    @Test(expected=NoSuchEntityException.class)
    public void shouldNotReopenRequestOfDeactivatedEmployee() throws Exception {
        // given
        request.setAssignee(assignee);
        request.setEstimateTimeInDays(3);
        request.setProgressStatus(ProgressStatus.CLOSED);
        requestService.update(request);

        // when
        userService.delete(request.getReporter());
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