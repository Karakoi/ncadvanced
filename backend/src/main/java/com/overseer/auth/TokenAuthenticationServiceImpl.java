package com.overseer.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Represents the service forming authentication according to the token.
 */
@Service
@RequiredArgsConstructor
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    private final TokenHandler tokenHandler;
    private static final Integer TOKEN_PLACEMENT = 7;

    /**
     * Return the UserAuthentication object according to the authorisation header.
     *
     * @param request object that contains the request the client has made of the servlet.
     * @return the UserAuthentication object.
     */
    public Authentication getAuthentication(HttpServletRequest request) {
        String authHeader = request.getHeader("authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return null;
        }
        // authHeader example
        // Bearer 1u2jio12h802f12k
        String jwt = authHeader.substring(TOKEN_PLACEMENT);
        if (jwt.isEmpty()) {
            return null;
        }
        UserDetails userDetails = this.tokenHandler.parseUserFromToken(jwt);
        return new UserAuthentication(userDetails);
    }
}

