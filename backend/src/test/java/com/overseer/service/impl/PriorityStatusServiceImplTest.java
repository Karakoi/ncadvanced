package com.overseer.service.impl;

import com.overseer.model.PriorityStatus;
import com.overseer.service.PriorityStatusService;
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
public class PriorityStatusServiceImplTest {

    @Autowired
    private PriorityStatusService priorityStatusService;

    @Test
    public void findAllPriorityStatuses() throws Exception {
        List<PriorityStatus> allPriorityStatuses = priorityStatusService.findAllPriorityStatuses();
        final int expectedStatusesSize = 3;
        Assert.assertNotNull(allPriorityStatuses);
        Assert.assertEquals(expectedStatusesSize, allPriorityStatuses.size());
    }
}