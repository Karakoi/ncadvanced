package com.overseer.service;

import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.Role;
import com.overseer.model.User;

import java.util.List;

/**
 * The <code>UserService</code> interface represents access to UserDao.
 */
public interface UserService extends CrudService<User, Long> {

    /**
     * Sends to email new generated password for existed {@link User}.
     *
     * @param email email for recovering password, must not be {@literal null}.
     */
    void changePassword(String email) throws NoSuchEntityException;

    /**
     * Fetches {@link User} entity by provided email.
     *
     * @param email user's email address, must not be {@literal null}.
     * @return {@link User} entity associated with provided email, or {@literal null} if none found.
     */
    User findByEmail(String email) throws NoSuchEntityException;

    /**
     * Returns a list of {@link User} entities who's roles match provided role.
     *
     * @param role user's role, must not be {@literal null}.
     * @return a list of {@link User} entities.
     */
    List<User> findByRole(Role role);
}
