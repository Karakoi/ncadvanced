package com.overseer.auth;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents the service forming authentication according to the token.
 */
public interface TokenAuthenticationService {
    Authentication getAuthentication(HttpServletRequest request);
}
