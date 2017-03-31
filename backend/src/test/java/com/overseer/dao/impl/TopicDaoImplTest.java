package com.overseer.dao.impl;

import com.overseer.dao.TopicDao;
import com.overseer.model.Role;
import com.overseer.model.Topic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TopicDaoImplTest {

    @Autowired
    private TopicDao topicDao;

    private Topic testTopic;

    private Long testTopicId;

    private Role role;

    @Test
    public void test(){

    }
    /*@Before
    public void setUp() throws Exception {
        role = new Role("admin");
        role.setId(10L);
        testTopic = new Topic("Some topic", role);
        testTopic = topicDao.save(testTopic);
        testTopicId = testTopic.getId();
    }

    @Test
    public void deleteById() throws Exception {
        topicDao.delete(testTopicId);
        final Topic actual = topicDao.findOne(testTopicId);
        Assert.assertNull(actual);
    }

    @Test
    public void deleteByValue() throws Exception {
        topicDao.delete(testTopic);
        final Topic actual = topicDao.findOne(testTopicId);
        Assert.assertNull(actual);
    }

    @Test
    public void exists() throws Exception {
        final boolean actual = topicDao.exists(testTopicId);
        Assert.assertTrue(actual);
    }*/
}