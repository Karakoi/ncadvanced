package com.overseer.service;

import com.overseer.model.Role;

import java.util.List;

/**
 * The <code>RoleService</code> interface represents access to RoleStatusDao.
 */
public interface RoleService extends CrudService<Role, Long> {

    /**
     * Returns all roles values.
     *
     * @return list of roles.
     */
    List<Role> findAllRoles();
}