package com.overseer;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.model.ProgressStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test for ProgressStatusDao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProgressStatusDaoTest {
    @Autowired
    private ProgressStatusDao progressStatusDao;

    @Test
    @Transactional
    @Rollback(true)
    public void testAddProgressStatus()
    {
        String nameProgressStatus = "Romanova";
        ProgressStatus progressStatus = new ProgressStatus(nameProgressStatus);
        ProgressStatus savedProgressStatus = progressStatusDao.save(progressStatus);
        ProgressStatus fromDbProgressStatus = progressStatusDao.findOne(savedProgressStatus.getId());

        Assert.assertEquals(fromDbProgressStatus.getName(), nameProgressStatus);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteProgressStatus()
    {
        String nameProgressStatus = "Romanova";
        ProgressStatus progressStatus = new ProgressStatus(nameProgressStatus);
        ProgressStatus savedProgressStatus = progressStatusDao.save(progressStatus);
        progressStatusDao.delete(savedProgressStatus);
        ProgressStatus fromDbProgressStatus = progressStatusDao.findOne(savedProgressStatus.getId());

        Assert.assertEquals(fromDbProgressStatus, null);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindByNameProgressStatus()
    {
        String nameProgressStatus = "Romanova";
        ProgressStatus progressStatus = new ProgressStatus(nameProgressStatus);
        ProgressStatus savedProgressStatus = progressStatusDao.save(progressStatus);
        ProgressStatus fromDbProgressStatus = progressStatusDao.findByName(nameProgressStatus);

        Assert.assertEquals(fromDbProgressStatus, savedProgressStatus);
    }
}
