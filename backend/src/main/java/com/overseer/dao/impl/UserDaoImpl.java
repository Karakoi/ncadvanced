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
        Assert.notNull(user.getPassword(), "User has to have a password");
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
        parameterSource.addValue("offset", pageSize * (pageNumber - 1));
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
        return this.queryService().getQuery("user.deactivate");
    }

    @Override
    protected String getExistsQuery() {
        return this.queryService().getQuery("user.exists");
    }

    @Override
    protected String getFindAllQuery() {
        return this.queryService().getQuery("user.findAll");
    }

    protected String getUserChatFriendsQuery() {
        return this.queryService().getQuery("user.findAllChatFriends");
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

            User user = new User();
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setRole(role);
            user.setId(resultSet.getLong("id"));
            user.setSecondName(resultSet.getString("second_name"));
            String dateOfBirth = resultSet.getString("date_of_birth");
            if (dateOfBirth != null) {
                user.setDateOfBirth(LocalDate.parse(dateOfBirth));
            }
            user.setPhoneNumber(resultSet.getString("phone_number"));
            try {
                user.setIsDeactivated(resultSet.getBoolean("is_deactivated"));
                user.setDateOfDeactivation(resultSet.getTimestamp("date_of_deactivation").toLocalDateTime());
            } catch (java.sql.SQLException e) {
                user.setIsDeactivated(null);
                user.setDateOfDeactivation(null);
            }
            return user;
        };
    }

    /**
     * {@inheritDoc}.
     */
    public List<User> findUserChatPartners(Long userId) {
        Assert.notNull(userId, "User can't be null");
        return this.jdbc().query(this.queryService().getQuery("user.findAllChatFriends"),
                new MapSqlParameterSource("userId", userId),
                this.getMapper());
    }

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("user.count");
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findManagersByEmployee(Long employeeId) {
        Assert.notNull(employeeId, "EmployeeId can't be null");
        return this.jdbc().query(this.queryService().getQuery("user.managers"),
                new MapSqlParameterSource("reporter", employeeId),
                this.getMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findUsersByManager(Long managerId) {
        Assert.notNull(managerId, "ManagerId can't be null");
        return this.jdbc().query(this.queryService().getQuery("manager.users"),
                new MapSqlParameterSource("assignee", managerId),
                this.getMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findAllDeactivated(int pageSize, int pageNumber) {
        Assert.state(pageNumber > 0, "Must be greater then 0");
        val parameterSource = new MapSqlParameterSource("limit", pageSize);
        parameterSource.addValue("offset", pageSize * (pageNumber - 1));
        String findAllDeactivatedQuery = this.queryService().getQuery("user.findAllDeactivated");
        return this.jdbc().query(findAllDeactivatedQuery, parameterSource, this.getMapper());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void activate(Long id) {
        Assert.notNull(id, "id must not be null");
        this.jdbc().update(this.queryService().getQuery("user.activate"), new MapSqlParameterSource("id", id));
    }

    @Override
    public Long getCountAllDeactivated() {
        return this.jdbc().queryForObject(this.queryService().getQuery("user.deactivatedCount"), new MapSqlParameterSource(), Long.class);
    }

    @Override
    public List<User> searchRequests(String searchQuery) {
        String query = this.queryService().getQuery("user.search").concat(searchQuery);
        try {
            return jdbc().query(query, this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }
}
