package com.overseer.service;

/**
 * The <code>UserService</code> interface represents access to UserDao.
 */
public interface UserService extends CrudService{

    /**
     * Sends to email new generated password for existed User.
     * @param email email for recovering password, must not be {@literal null}.
     */
    void changePassword(String email);

}
