package com.overseer.service;

import com.overseer.model.User;

/**
 * The <code>UserService</code> interface represents access to UserDao.
 */
public interface UserService extends CrudService<User, Long>{

    /**
     * Sends to email new generated password for existed {@link User}.
     * @param email email for recovering password, must not be {@literal null}.
     */
    void changePassword(String email);

}
