package com.overseer.dao.impl;

import com.overseer.dao.UserDao;
import com.overseer.model.Role;
import com.overseer.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    private User testUser;

    @Before
    public void setUp() throws Exception {
        Role role = new Role("employee");
        role.setId(12L);
        this.testUser = new User("Mark", "Biggles", "securepass22", "valid@email.com", role);
        this.userDao.save(testUser);
    }

    @Test
    public void shouldSaveUser() throws Exception {
        // given

        // when
        User savedUser = userDao.findOne(testUser.getId());

        // then
        assertThat(savedUser, is(testUser));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        // given
        String testLastName = "Rainbow";
        testUser.setLastName(testLastName);

        // when
        User savedUser = userDao.save(testUser);

        // then
        assertThat(savedUser, is(notNullValue()));
        assertThat(savedUser.getLastName(), is(testLastName));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        // given

        // when
        userDao.delete(testUser);
        User savedUser = userDao.findOne(testUser.getId());

        // then
        assertThat(savedUser, is(nullValue()));
    }

    @Test
    public void shouldFindUserByEmail() throws Exception {
        // given

        // when
        User savedUser = userDao.findByEmail(testUser.getEmail());

        // then
        assertThat(savedUser, is(testUser));
    }

    @Test
    public void shouldFindUserByRole() throws Exception {
        // given

        // when
        List<User> savedUsers = userDao.findByRole(testUser.getRole());

        // then
        assertThat(savedUsers, is(notNullValue()));
        assertThat(savedUsers, is(not(empty())));
    }
}