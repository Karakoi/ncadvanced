package com.overseer.dao.impl;

import com.overseer.dao.UserDao;
import com.overseer.model.Role;
import com.overseer.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * Implementation of {@link UserDao} interface.
 * </p>
 */
@Transactional
@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private static final String SELECT_ALL_USERS = "SELECT * FROM \"user\" u ";

    private static final String SELECT_USER_BY_ID = "SELECT * FROM \"user\" u WHERE u.id = :id";

    private static final String INSERT_USER = "INSERT INTO \"user\" (first_name, last_name, second_name, password, email, "
            + "date_of_birth, phone_number, role_id) "
            + "VALUES (:firstName, :lastName, :secondName, :password, "
            + ":email, :dateOfBirth, :phoneNumber, :roleId) "
            + "ON CONFLICT (id) DO UPDATE SET first_name = :firstName, last_name = :lastName, "
            + "second_name = :secondName, password = :password, "
            + "email = :email, date_of_birth = :dateOfBirth, phone_number = :phoneNumber, "
            + "role_id = :role.id";

    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM \"user\" u WHERE u.email LIKE :email";

    private static final String DELETE_USER_BY_ID = "DELETE FROM \"user\" WHERE id = :id";

    private static final String SELECT_USERS_BY_ROLE = "SELECT * FROM \"user\" u WHERE u.role_id = :roleId";

    private static final String EXISTS_USER_ID = "SELECT COUNT(*) FROM \"user\" WHERE id = :id";

    private final NamedParameterJdbcOperations jdbc;

    /**
     * {@inheritDoc}.
     *
     * @throws {@link org.springframework.dao.DuplicateKeyException} if email unique constraint violated.
     */
    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(user);
        if (user.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_USER, sqlParameterSource, keyHolder, new String[]{"id"});
            user.setId(keyHolder.getKey().longValue());
        } else {
            jdbc.update(INSERT_USER, sqlParameterSource);
        }
        return user;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User findOne(Long id) {
        Assert.notNull(id, "id must not be null");
        try {
            return jdbc.queryForObject(SELECT_USER_BY_ID,
                    new MapSqlParameterSource("id", id),
                    BeanPropertyRowMapper.newInstance(User.class));
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(User user) {
        Assert.notNull(user, "user must not be null");
        delete(user.getId());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id, "id must not be null");
        jdbc.update(DELETE_USER_BY_ID,
                new MapSqlParameterSource("id", id));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        Assert.notNull(id, "id must not be null");
        return jdbc.queryForObject(EXISTS_USER_ID,
                new MapSqlParameterSource("id", id), Integer.class) > 0;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findAll() {
        return jdbc.query(SELECT_ALL_USERS, BeanPropertyRowMapper.newInstance(User.class));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User findByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        try {
            return jdbc.queryForObject(SELECT_USER_BY_EMAIL,
                    new MapSqlParameterSource("email", email),
                    BeanPropertyRowMapper.newInstance(User.class));
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findByRole(Role role) {
        Assert.notNull(role, "role must not be null");
        return jdbc.query(SELECT_USERS_BY_ROLE,
                new MapSqlParameterSource("role_id", role.getId()),
                BeanPropertyRowMapper.newInstance(User.class));
    }

}
