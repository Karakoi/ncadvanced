package com.overseer.dao.impl;

import com.overseer.dao.UserDao;
import com.overseer.model.Role;
import com.overseer.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Implementation of {@link UserDao} interface.
 * </p>
 */
@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    private static final String SELECT_ALL_USERS = "SELECT * FROM \"user\" u ";

    private static final String SELECT_USER_BY_ID = "SELECT * FROM \"user\" u "
            + "WHERE u.id = :id";

    private static final String INSERT_USER = "INSERT INTO \"user\" "
            + "(first_name, last_name, second_name, password, email, "
            + "date_of_birth, phone_number, role) VALUES ("
            + " :firstName, :lastName, :secondName, :password, "
            + ":email, :dateOfBirth, :phoneNumber, :role ::role)";

    private static final String UPDATE_USER_BY_ID = "UPDATE \"user\" SET "
            + "first_name = :firstName, last_name = :lastName, "
            + "second_name = :secondName, password = :password, "
            + "email = :email, date_of_birth = :dateOfBirth, phone_number = :phoneNumber, "
            + " role = :role ::role WHERE id = :id";

    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM \"user\" u "
            + " WHERE u.email LIKE :email";

    private static final String DELETE_USER_BY_ID = "DELETE FROM \"user\" WHERE id = :id";

    private static final String SELECT_USERS_BY_ROLE = "SELECT * FROM \"user\" u "
            + " WHERE u.role = :role ::role";
    
    private static final String EXISTS_USER_ID = "SELECT COUNT(*) FROM \"user\" WHERE id = :id";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * {@inheritDoc}.
     * @throws {@link org.springframework.dao.DuplicateKeyException} if email unique constraint violated.
     */
    @Override
    public User save(User user) {
        Assert.notNull(user);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("firstName", user.getFirstName());
        namedParameters.addValue("lastName", user.getLastName());
        namedParameters.addValue("password", user.getPassword());
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("role", user.getRole().toString());
        namedParameters.addValue("secondName", user.getSecondName());
        LocalDate dateOfBirth = user.getDateOfBirth();
        if (dateOfBirth != null) {
            namedParameters.addValue("dateOfBirth", Date.valueOf(dateOfBirth));
        } else {
            namedParameters.addValue("dateOfBirth", null);
        }
        namedParameters.addValue("phoneNumber", user.getPhoneNumber());
        if (user.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(INSERT_USER, namedParameters, 
                    keyHolder, new String[]{"id"});
            user.setId(keyHolder.getKey().longValue());
        } else {
            namedParameters.addValue("id", user.getId());
            namedParameterJdbcTemplate.update(UPDATE_USER_BY_ID, namedParameters);
        }
        return user;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User findOne(Long id) {
        Assert.notNull(id);
        List<User> users = namedParameterJdbcTemplate.query(SELECT_USER_BY_ID, 
                new MapSqlParameterSource("id", id),
                new UserMapper());
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(User user) {
        Assert.notNull(user);
        delete(user.getId()); 
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        namedParameterJdbcTemplate.update(DELETE_USER_BY_ID, 
                new MapSqlParameterSource("id", id));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        return namedParameterJdbcTemplate.queryForObject(EXISTS_USER_ID, 
                new MapSqlParameterSource("id", id), Integer.class) > 0;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findAll() {
        return namedParameterJdbcTemplate.query(SELECT_ALL_USERS, new UserMapper());
        
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User findByEmail(String email) {
        Assert.notNull(email);
        List<User> users = namedParameterJdbcTemplate.query(SELECT_USER_BY_EMAIL,
                new MapSqlParameterSource("email", email), new UserMapper());
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findByRole(Role role) {
        Assert.notNull(role);
        return namedParameterJdbcTemplate.query(SELECT_USERS_BY_ROLE,
                new MapSqlParameterSource("role", role.toString()), 
                new UserMapper());
    }

    /**
     * Class maps query results to {@link User} class.
     */
    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User(resultSet.getString("first_name"), resultSet.getString("last_name"),
                    resultSet.getString("password"), resultSet.getString("email"),
                    Role.getValueFromString(resultSet.getString("role")));
            user.setId(resultSet.getLong("id"));
            user.setSecondName(resultSet.getString("second_name"));
            String dateOfBirth = resultSet.getString("date_of_birth");
            if (dateOfBirth != null) {
                user.setDateOfBirth(LocalDate.parse(dateOfBirth));
            }
            user.setPhoneNumber(resultSet.getString("phone_number"));
            return user;
        }
    }

}
