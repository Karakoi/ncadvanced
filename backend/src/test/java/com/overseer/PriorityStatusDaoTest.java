package com.overseer;

import com.overseer.dao.PriorityStatusDao;
import com.overseer.model.PriorityStatus;
import org.junit.After;
import org.junit.Before;
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
 *  Test for {@link PriorityStatusDao}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriorityStatusDaoTest {
    @Autowired
    private PriorityStatusDao priorityStatusDao;

    private static final String TEST_NAME = "PriorityStatus";
    private PriorityStatus savedPriorityStatus;

    @Before
    public void setUp() throws Exception {
        savedPriorityStatus = new PriorityStatus(TEST_NAME);
        priorityStatusDao.save(savedPriorityStatus);
    }

    @After
    public void tearDown() throws Exception {
        savedPriorityStatus = null;
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAddPriorityStatus() {
        // given

        // when
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.findOne(savedPriorityStatus.getId());

        // then
        assertThat(fromDbPriorityStatus.getName(), is(TEST_NAME));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testDeletePriorityStatus() {
        // given

        // when
        priorityStatusDao.delete(savedPriorityStatus);
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.findOne(savedPriorityStatus.getId());

        // then
        assertThat(fromDbPriorityStatus, is(nullValue()));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testFindByNamePriorityStatus() {
        // given

        // when
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.findByName(TEST_NAME);

        // then
        assertThat(fromDbPriorityStatus, is(savedPriorityStatus));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdatePriorityStatus() {
        // given
        String updatedName = "TestStatus";
        savedPriorityStatus.setName(updatedName);

        // when
        PriorityStatus fromDbPriorityStatus = priorityStatusDao.save(savedPriorityStatus);

        // then
        assertThat(fromDbPriorityStatus.getName(), is(updatedName));
    }
}
