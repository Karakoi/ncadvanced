package com.overseer.dao;

import com.overseer.model.Role;
import com.overseer.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The <code>UserDao</code> interface represents access to {@link User} object in database.
 */

public interface UserDao extends CrudDao<User, Long> {

    /**
     * Fetches {@link User} entity by provided email.
     *
     * @param email user's email address, must not be {@literal null}.
     * @return {@link User} entity associated with provided email, or {@literal null} if none found.
     */
    User findByEmail(String email);


    /**
     * Returns a list of {@link User} entities who's roles match provided role.
     *
     * @param role user's role, must not be {@literal null}.
     * @return a list of {@link User} entities.
     */
    List<User> findByRole(Role role);

}
