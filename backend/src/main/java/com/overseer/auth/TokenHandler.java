package com.overseer.auth;

import com.overseer.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Represents the service for working with tokens.
 */
@Component
public interface TokenHandler {

    UserDetails parseUserFromToken(String token);

    String createTokenForUser(User user);

}
