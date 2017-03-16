package com.overseer.service;

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
    void changePassword(String email);

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
    List<User> findByRole(Role role, int pageNumber);

    /**
     * Method find managers which work on employee tasks.
     * @param employeeId employee which have a tasks.
     * @return list of office managers which works on employee's tasks.
     */
    List<User> findManagersByEmployee(Long employeeId);

    /**
     * Method find employees by tasks which has been assigned by manager.
     * @param managerId manager which work on tasks.
     * @return list of employees.
     */
    List<User> findUsersByManager(Long managerId);

    /**
     * Returns a list of {@link User} entities who's deactivated.
     *
     * @param pageNumber number of page.
     * @return a list of {@link User} entities.
     */
    List<User> findAllDeactivated(int pageNumber);

    /**
     * Activates {@link User} entity with given id.
     *
     * @param id user's id, must not be {@literal null}.
     */
    void activate(Long id);

}
