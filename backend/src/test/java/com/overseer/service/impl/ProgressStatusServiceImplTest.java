package com.overseer.service.impl;

import com.overseer.model.ProgressStatus;
import com.overseer.service.ProgressStatusService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProgressStatusServiceImplTest {

    @Autowired
    private ProgressStatusService progressStatusService;

    @Test
    public void findAllPriorityStatuses() throws Exception {
        List<ProgressStatus> allProgressStatuses = progressStatusService.findAllProgressStatuses();
        final int expectedStatusesSize = 4;
        Assert.assertNotNull(allProgressStatuses);
        Assert.assertEquals(expectedStatusesSize, allProgressStatuses.size());
    }
}