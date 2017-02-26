package com.overseer;

import com.overseer.dao.PriorityStatusDao;
import com.overseer.model.PriorityStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 *  Test for PriorityStatusDao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriorityStatusDaoTest {
    @Autowired
    private PriorityStatusDao priorityStatusDao;

    @Test
    @Transactional
    @Rollback(true)
    public void testAddPriorityStatus()
    {
        // given
        String namePriorityStatus = "PriorityStatus";
        PriorityStatus priorityStatus = new PriorityStatus(namePriorityStatus);
        PriorityStatus savedPriorityStatus = priorityStatusDao.save(priorityStatus);

        // when
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.findOne(savedPriorityStatus.getId());

        // then
        assertThat(fromDbPriorityStatus.getName(), is(namePriorityStatus));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeletePriorityStatus()
    {
        // given
        String namePriorityStatus = "PriorityStatus";
        PriorityStatus priorityStatus = new PriorityStatus(namePriorityStatus);
        PriorityStatus savedPriorityStatus = priorityStatusDao.save(priorityStatus);

        // when
        priorityStatusDao.delete(savedPriorityStatus);
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.findOne(savedPriorityStatus.getId());

        // then
        assertThat(fromDbPriorityStatus, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindByNamePriorityStatus()
    {
        // given
        String namePriorityStatus = "PriorityStatus";
        PriorityStatus priorityStatus = new PriorityStatus(namePriorityStatus);
        PriorityStatus savedPriorityStatus = priorityStatusDao.save(priorityStatus);

        // when
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.findByName(namePriorityStatus);

        // then
        assertThat(fromDbPriorityStatus, is(savedPriorityStatus));
    }

}
