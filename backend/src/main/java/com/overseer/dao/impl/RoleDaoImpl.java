package com.overseer.dao.impl;

import com.overseer.dao.RoleDao;
import com.overseer.model.Role;
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
 * Implementation of {@link RoleDao} interface.
 * </p>
 */
@Transactional
@Repository
@RequiredArgsConstructor
public class RoleDaoImpl implements RoleDao {
    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM role r WHERE r.id = :id";

    private static final String SELECT_ROLE_BY_NAME = "SELECT * FROM role r WHERE r.name = :roleName";

    private static final String SELECT_ALL_ROLES = "SELECT * FROM role";

    private static final String INSERT_ROLE =
            "INSERT INTO role (name) VALUES (:name) ON CONFLICT (id) DO UPDATE SET name = excluded.name";

    private static final String DELETE_ROLE_BY_ID = "DELETE FROM role r WHERE r.id = :id";

    private final NamedParameterJdbcOperations jdbc;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Role save(Role role) {
        Assert.notNull(role, "role must not be null");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(role);
        if (role.getId() == null) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(INSERT_ROLE, sqlParameterSource, keyHolder, new String[]{"id"});
            long generatedId = keyHolder.getKey().longValue();
            role.setId(generatedId);
        } else {
            jdbc.update(INSERT_ROLE, sqlParameterSource);
        }
        return role;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Role findOne(Long id) {
        Assert.notNull(id, "ID must not be null");
        try {
            return jdbc.queryForObject(SELECT_ROLE_BY_ID,
                    new MapSqlParameterSource("id", id),
                    BeanPropertyRowMapper.newInstance(Role.class));
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Role role) {
        Assert.notNull(role, "role must not be null");
        delete(role.getId());
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id, "ID must not be null");
        jdbc.update(DELETE_ROLE_BY_ID, new MapSqlParameterSource("id", id));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        Assert.notNull(id, "ID must not be null");
        return findOne(id) != null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Role> findAll() {
        return jdbc.query(SELECT_ALL_ROLES, BeanPropertyRowMapper.newInstance(Role.class));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Role findByName(String name) {
        Assert.notNull(name, "name must not be null");
        try {
            return jdbc.queryForObject(SELECT_ROLE_BY_NAME,
                    new MapSqlParameterSource("roleName", name),
                    BeanPropertyRowMapper.newInstance(Role.class));
        } catch (DataAccessException e) {
            return null;
        }
    }
}
