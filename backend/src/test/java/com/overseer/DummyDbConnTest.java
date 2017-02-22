package com.overseer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Dummy test for establishing connection to remote Heroku database.
 * Will be deleted when first DAO impl created.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DummyDbConnTest {

    @Autowired
    private JdbcTemplate jt;

    @Test
    public void connectionTest() {
        jt.execute("DROP TABLE IF EXISTS conn_test");
        jt.execute("CREATE table conn_test (value varchar(20))");
        jt.execute("INSERT INTO conn_test (value) VALUES ('Connection success!')");
    }
}
