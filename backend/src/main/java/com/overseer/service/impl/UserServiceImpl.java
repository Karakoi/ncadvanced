package com.overseer.service.impl;

import com.overseer.model.User;
import com.overseer.service.UserService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Implementation of {@link UserService} interface.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * @{inheritDoc}.
     */
    @Override
    public void changePassword(String email) {

    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public User save(User entity) {
        return null;
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public User findOne(Long aLong) {
        return null;
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public void delete(User entity) {

    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public void delete(Long aLong) {

    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public List<User> findAll() {
        return null;
    }
}
