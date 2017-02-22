package com.overseer.auth;

import com.overseer.model.User;

/**
 * Repository for user data loading.
 */
public interface UserRepository {
    User findOneByUsername(String username);
}
