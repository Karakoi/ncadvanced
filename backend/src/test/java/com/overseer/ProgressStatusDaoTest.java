package com.overseer;

import com.overseer.dao.ProgressStatusDao;
import com.overseer.model.ProgressStatus;
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
 *  Test for ProgressStatusDao
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
        // given
        String nameProgressStatus = "ProgressStatus";
        ProgressStatus progressStatus = new ProgressStatus(nameProgressStatus);
        ProgressStatus savedProgressStatus = progressStatusDao.save(progressStatus);

        // when
        ProgressStatus fromDbProgressStatus = progressStatusDao.findOne(savedProgressStatus.getId());

        // then
        assertThat(fromDbProgressStatus.getName(), is(nameProgressStatus));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeleteProgressStatus()
    {
        // given
        String nameProgressStatus = "ProgressStatus";
        ProgressStatus progressStatus = new ProgressStatus(nameProgressStatus);
        ProgressStatus savedProgressStatus = progressStatusDao.save(progressStatus);

        // when
        progressStatusDao.delete(savedProgressStatus);
        ProgressStatus fromDbProgressStatus = progressStatusDao.findOne(savedProgressStatus.getId());

        // then
        assertThat(fromDbProgressStatus, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindByNameProgressStatus()
    {
        // given
        String nameProgressStatus = "ProgressStatus";
        ProgressStatus progressStatus = new ProgressStatus(nameProgressStatus);
        ProgressStatus savedProgressStatus = progressStatusDao.save(progressStatus);

        // when
        ProgressStatus fromDbProgressStatus = progressStatusDao.findByName(nameProgressStatus);

        // then
        assertThat(fromDbProgressStatus, is(savedProgressStatus));
    }
}
