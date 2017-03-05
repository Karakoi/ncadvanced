package com.overseer.dao.impl;

import com.overseer.dao.RoleDao;
import com.overseer.model.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Implementation of {@link RoleDao} interface.
 * </p>
 */
@Repository
public class RoleDaoImpl extends SimpleEntityDaoImpl<Role> implements RoleDao {

    @Override
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
}
