package com.overseer.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents the service forming authentication according to the token.
 */
@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private final TokenHandler tokenHandler;

    @Autowired
    TokenAuthenticationServiceImpl(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    /**
     * Return the UserAuthentication object according to the authorisation header.
     *
     * @param request object that contains the request the client has made of the servlet.
     * @return the UserAuthentication object.
     */
    public Authentication getAuthentication(HttpServletRequest request) {
        final String AUTHHEADER = request.getHeader("authorization");
        if (AUTHHEADER == null || !AUTHHEADER.startsWith("Bearer")) {
            return null;
        }
        final String JWT = AUTHHEADER.substring(7);
        if (JWT.isEmpty()) {
            return null;
        }
        return new UserAuthentication(tokenHandler.parseUserFromToken(JWT));
    }
}

