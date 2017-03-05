package com.overseer.dao.impl;

import com.overseer.dao.UserDao;
import com.overseer.model.Role;
import com.overseer.model.User;
import lombok.val;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Implementation of {@link UserDao} interface.
 * </p>
 */
@Repository
public class UserDaoImpl extends CrudDaoImpl<User> implements UserDao {

    /**
     * {@inheritDoc}.
     */
    @Override
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        user = this.encryptPassword(user);
        return super.save(user);
    }

    /**
     * Encodes password using {@link BCryptPasswordEncoder}.
     *
     * @param user user to encode password for.
     * @return user entity with encoded password.
     */
    private User encryptPassword(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = user.getPassword();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        return user;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User findByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        try {
            return this.jdbc().queryForObject(this.queryService().getQuery("user.findByEmail"),
                    new MapSqlParameterSource("email", email),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findByRole(Role role, int pageSize, int pageNumber) {
        Assert.notNull(role, "role must not be null");
        val parameterSource = new MapSqlParameterSource("limit", pageSize);
        parameterSource.addValue("offset", pageSize * pageNumber - 1);
        parameterSource.addValue("role", role.getId());
        return this.jdbc().query(this.queryService().getQuery("user.findByRole"),
                parameterSource,
                this.getMapper());
    }

    @Override
    protected String getInsertQuery() {
        return this.queryService().getQuery("user.insert");
    }

    @Override
    protected String getFindOneQuery() {
        return this.queryService().getQuery("user.findOne");
    }

    @Override
    protected String getDeleteQuery() {
        return this.queryService().getQuery("user.delete");
    }

    @Override
    protected String getExistsQuery() {
        return this.queryService().getQuery("user.exists");
    }

    @Override
    protected String getFindAllQuery() {
        return this.queryService().getQuery("user.findAll");
    }

    /**
     * Returns {@link RowMapper} implementation for {@link User} entity.
     *
     * @return {@link RowMapper} implementation for {@link User} entity.
     */
    @Override
    protected RowMapper<User> getMapper() {
        return (resultSet, i) -> {
            Role role = new Role(resultSet.getString("name"));
            role.setId(resultSet.getLong("role"));

            User user = new User(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    role);
            user.setId(resultSet.getLong("id"));
            user.setSecondName(resultSet.getString("second_name"));
            String dateOfBirth = resultSet.getString("date_of_birth");
            if (dateOfBirth != null) {
                user.setDateOfBirth(LocalDate.parse(dateOfBirth));
            }
            user.setPhoneNumber(resultSet.getString("phone_number"));
            return user;
        };
    }

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("user.count");
    }
}
