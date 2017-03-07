package com.overseer.dao.impl;

import com.overseer.dao.HistoryDAO;
import com.overseer.model.History;
import com.overseer.model.Role;
import com.overseer.model.Topic;
import com.overseer.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 *  Test for {@link HistoryDAO}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class HistoryDaoImplTest {

    @Autowired
    private HistoryDAO historyDAO;

    private History history1;
    private History history2;

    private static final String TEST_COLUMN_NAME = "title name of topic";
    private static final LocalDateTime TEST_DATE_OF_LAST_CHANGES = LocalDateTime.parse("2007-12-03T00:00:00");
    private static final User TEST_CHANGER = new User("firstName", "lastName", "pass", "123#gmail.com", new Role("admin"));
    private static final Long TEST_RECORD_ID = 9999999L;
    private static Topic topic = new Topic("Amazing topic");

    static {
        topic.setId(TEST_RECORD_ID);
    }

    private void creationOfSecondHistoryObject(){
        history2 = new History();
        history2.setColumnName(TEST_COLUMN_NAME);
        history2.setOldValue("old title 2");
        history2.setNewValue("new title 2");
        history2.setDateOfLastChange(TEST_DATE_OF_LAST_CHANGES);
        history2.setChanger(TEST_CHANGER);
        history2.setRecordId(topic.getId());

        history2 =  historyDAO.save(history2);

    }

    @Before
    public void setUp() throws Exception{
        history1 = new History();
        history1.setColumnName(TEST_COLUMN_NAME);
        history1.setOldValue("old title 1");
        history1.setNewValue("new title 1");
        history1.setDateOfLastChange(TEST_DATE_OF_LAST_CHANGES);
        history1.setChanger(TEST_CHANGER);
        history1.setRecordId(topic.getId());

        history1 = historyDAO.save(history1);
    }

    @Test
    public void findAllForEntity() throws Exception {
        // given
        creationOfSecondHistoryObject();

        // when
        List<History> allHistoryForEntity = historyDAO.findAllForEntity(TEST_RECORD_ID);

        // then
        List<History> expectedList = Arrays.asList(history1, history2);
        assertThat(allHistoryForEntity, is(expectedList));
    }


    @Test
    public void findOne() throws Exception {
        // given

        // when
        History actualHistory = historyDAO.findOne(history1.getId());

        // then
        assertThat(actualHistory, is(history1));
    }

    @Test
    public void deleteById() throws Exception {
        // given

        // when
        historyDAO.delete(history1.getId());
        History actualHistory = historyDAO.findOne(history1.getId());

        // then
        assertNull(actualHistory);
    }

    @Test
    public void deleteByEntity() throws Exception {
        // given

        // when
        historyDAO.delete(history1);
        History actualHistory = historyDAO.findOne(history1.getId());

        // then
        assertNull(actualHistory);
    }

    @Test
    public void exists() throws Exception {
        // given

        // when
        Boolean isEntityInDB = historyDAO.exists(history1.getId());

        // then
        assertTrue(isEntityInDB);
    }
}