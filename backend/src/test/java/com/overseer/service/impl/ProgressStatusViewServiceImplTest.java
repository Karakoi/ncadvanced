package com.overseer.service.impl;

import com.overseer.model.ProgressStatusView;
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
public class ProgressStatusViewServiceImplTest {

    @Autowired
    private ProgressStatusUtil progressStatusUtil;

    @Test
    public void findAllPriorityStatuses() throws Exception {
        List<ProgressStatusView> allProgressStatusViews = progressStatusUtil.getAllProgressStatusViews();
        final int expectedStatusesSize = 4;
        Assert.assertNotNull(allProgressStatusViews);
        Assert.assertEquals(expectedStatusesSize, allProgressStatusViews.size());
    }
}