package com.overseer.dao.impl;

import com.overseer.dao.RoleDao;
import com.overseer.model.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 *  Test for {@link RoleDao}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class RoleDaoImplTest {

    @Autowired
    private RoleDao roleDao;

    private static final String TEST_NAME = "Role";
    private Role savedRole;

    @Before
    public void setUp() throws Exception {
        savedRole = new Role(TEST_NAME);
        roleDao.save(savedRole);
    }

    @After
    public void tearDown() throws Exception {
        savedRole = null;
    }

    @Test
    public void testAddRole() {
        // given

        // when
        Role fromDbRole = roleDao.findOne(savedRole.getId());

        // then
        assertThat(fromDbRole.getName(), is(TEST_NAME));
    }

    @Test
    public void testDeleteRole() {
        // given

        // when
        roleDao.delete(savedRole);
        Role fromDbRole = roleDao.findOne(savedRole.getId());

        // then
        assertThat(fromDbRole, is(nullValue()));
    }

    @Test
    public void testFindByNameRole() {
        // given

        // when
        Role fromDbRole = roleDao.findByName(TEST_NAME);

        // then
        assertThat(fromDbRole, is(savedRole));
    }

    @Test
    public void testUpdateRole() {
        // given
        String updatedName = "TestRole";
        savedRole.setName(updatedName);

        // when
        Role fromDbRole = roleDao.save(savedRole);

        // then
        assertThat(fromDbRole.getName(), is(updatedName));
    }
}
