package com.overseer.dao;

import com.overseer.model.Role;

import java.util.List;

/**
 *  The <code>RoleDao</code> interface represents access to {@link Role} object in database.
 */
public interface RoleDao extends CrudDao<Role, Long> {

    /**
     * Method find all roles.
     * @return list of roles.
     */
    List<Role> findAllRoles();

    /**
     * Fetches role by its name.
     *
     * @param name must not be {@literal null}.
     * @return role with the given name or {@literal null} if none found.
     */
    Role findByName(String name);
}
