package com.overseer.auth;

import com.overseer.model.User;

/**
 * Represents service for current user obtaining.
 */
public interface SecurityContextService {

    User currentUser();

}
