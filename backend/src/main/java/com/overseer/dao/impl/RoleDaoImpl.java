package com.overseer.dao.impl;

import com.overseer.dao.RoleDao;
import com.overseer.model.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * <p>
 * Implementation of {@link RoleDao} interface.
 * </p>
 */
@Repository
public class RoleDaoImpl extends CrudDaoImpl<Role> implements RoleDao {

    protected String getFindByNameQuery() {
        return this.queryService().getQuery("role.findByName");
    }

    @Override
    protected String getInsertQuery() {
        return this.queryService().getQuery("role.insert");
    }

    @Override
    protected String getFindOneQuery() {
        return this.queryService().getQuery("role.findOne");
    }

    @Override
    protected String getDeleteQuery() {
        return this.queryService().getQuery("role.delete");
    }

    @Override
    protected String getExistsQuery() {
        return this.queryService().getQuery("role.exists");
    }

    @Override
    protected String getFindAllQuery() {
        return this.queryService().getQuery("role.findAll");
    }

    @Override
    protected String getCountQuery() {
        return this.queryService().getQuery("role.count");
    }

    /**
     * Gets {@link RowMapper} implementation for {@link Role} entity.
     *
     * @return {@link RowMapper} implementation for {@link Role} entity.
     */
    @Override
    protected RowMapper<Role> getMapper() {
        return (resultSet, i) -> {
            Role role = new Role(resultSet.getString("name"));
            role.setId(resultSet.getLong("id"));
            return role;
        };
    }

    @Override
    public List<Role> findAllRoles() {
        return this.jdbc().query(getFindAllQuery(),
                this.getMapper());
    }

    @Override
    public Role findByName(String name) {
        Assert.notNull(name, "name must not be null");
        try {
            return this.jdbc().queryForObject(getFindByNameQuery(),
                    new MapSqlParameterSource("name", name),
                    this.getMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

}
