package com.overseer.dao.impl;

import com.overseer.dao.UserDao;
import com.overseer.model.Role;
import com.overseer.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 * Implementation of {@link UserDao} interface.
 * </p>
 */
@RequiredArgsConstructor
@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    private static final String SELECT_ALL_USERS = "SELECT * FROM user u " + "INNER JOIN role r ON u.role_id = r.id";

    private static final String SELECT_USER_BY_ID = "SELECT * FROM user u " + "INNER JOIN role r ON u.role_id = r.id "
            + "WHERE id = :id";

    private static final String INSERT_USER = "INSERT INTO user (" + "firstName, lastName, secondName, password, "
            + "email, dateOfBirth, phoneNumber, role_id) " + "VALUES ("
            + ":firstName, :lastName, :secondName, :password,"
            + ":email, :dateOfBirth, :phoneNumber, ( SELECT id FROM role WHERE name = :rolename ) )";

    private static final String UPDATE_USER_BY_ID = "UPDATE user SET "
            + "firstName = :firstName, lastName = :lastName, " + "secondName = :secondName, password = :password, "
            + "email = :email, dateOfBirth = :dateOfBirth, "
            + "phoneNumber = :phoneNumber, role_id = ( SELECT id FROM role WHERE name = :rolename ) "
            + "WHERE id = :id";

    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM user u "
            + "INNER JOIN role r ON u.role_id = r.id " + "WHERE email = :email";

    private static final String DELETE_USER_BY_ID = "DELETE FROM user WHERE id = :id";

    private static final String SELECT_USERS_BY_ROLE = "SELECT * FROM user u "
            + "INNER JOIN role r ON u.role_id = r.id " + "WHERE name = :rolename";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User save(User user) {
        Assert.notNull(user);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("firstName", user.getFirstName());
        namedParameters.addValue("lastName", user.getLastName());
        namedParameters.addValue("secondName", user.getSecondName());
        namedParameters.addValue("password", user.getPassword());
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("dateOfBirth", user.getDateOfBirth().toString());
        namedParameters.addValue("phoneNumber", user.getPhoneNumber());
        namedParameters.addValue("rolename", user.getRole().toString());
        if (findOne(user.getId()) != null) {
            namedParameters.addValue("id", user.getId());
            namedParameterJdbcTemplate.update(UPDATE_USER_BY_ID, namedParameters);
            return findOne(user.getId());
        } else {
            KeyHolder holder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(INSERT_USER, namedParameters, holder);
            return findOne(holder.getKey().longValue());
        }
    }

    @Override
    public User findOne(Long id) { 
        Assert.notNull(id);
        return namedParameterJdbcTemplate.queryForObject(SELECT_USER_BY_ID, new MapSqlParameterSource("id", id), new UserMapper());
    }

    @Override
    public void delete(User user) {
        Assert.notNull(user);
        delete(user.getId());
    }

    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        namedParameterJdbcTemplate.update(DELETE_USER_BY_ID, new MapSqlParameterSource("id", id));
    }

    @Override
    public boolean exists(Long id) {
        Assert.notNull(id);
        return findOne(id) != null;
    }

    @Override
    public List<User> findAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_USERS, new UserMapper());
    }

    @Override
    public User findByEmail(String email) {
        Assert.notNull(email);
        return namedParameterJdbcTemplate.queryForObject(SELECT_USER_BY_EMAIL, new MapSqlParameterSource("email", email), new UserMapper());
    }

    @Override
    public List<User> findByRole(Role role) { 
        Assert.notNull(role);
        return namedParameterJdbcTemplate.query(SELECT_USERS_BY_ROLE, new MapSqlParameterSource("rolename", role.toString()), new UserMapper());
    }

    /**
     * Class maps query results to {@link User} class.
     */
    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    Role.valueOf(resultSet.getString("name").toUpperCase(Locale.ENGLISH))
                    );
            user.setId(resultSet.getLong("id"));
            user.setSecondName(resultSet.getString("secondName"));
            user.setDateOfBirth(LocalDate.parse(resultSet.getString("dateOfBirth")));
            user.setPhoneNumber(resultSet.getString("phoneNumber"));
            return user;
        }
    }
}

