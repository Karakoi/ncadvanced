package com.overseer;

import com.overseer.dao.PriorityStatusDao;
import com.overseer.model.PriorityStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
        String namePriorityStatus = "Romanova";
        PriorityStatus priorityStatus = new PriorityStatus(namePriorityStatus);
        PriorityStatus savedPriorityStatus = priorityStatusDao.save(priorityStatus);
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.findOne(savedPriorityStatus.getId());

        Assert.assertEquals(fromDbPriorityStatus.getName(), namePriorityStatus);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeletePriorityStatus()
    {
        String namePriorityStatus = "Romanova";
        PriorityStatus priorityStatus = new PriorityStatus(namePriorityStatus);
        PriorityStatus savedPriorityStatus = priorityStatusDao.save(priorityStatus);
        priorityStatusDao.delete(savedPriorityStatus);
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.findOne(savedPriorityStatus.getId());

        Assert.assertEquals(fromDbPriorityStatus, null);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindByNamePriorityStatus()
    {
        String namePriorityStatus = "Romanova";
        PriorityStatus priorityStatus = new PriorityStatus(namePriorityStatus);
        PriorityStatus savedPriorityStatus = priorityStatusDao.save(priorityStatus);
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.findByName(namePriorityStatus);

        Assert.assertEquals(fromDbPriorityStatus, savedPriorityStatus);
    }

}
