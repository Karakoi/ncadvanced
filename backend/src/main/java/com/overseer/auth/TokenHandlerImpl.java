package com.overseer.auth;

import com.overseer.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Represents the service for working with tokens.
 */
@Component("tokenHandler")
public class TokenHandlerImpl implements TokenHandler {

    private final String secret;
    private final UserDetailsService userDetailsService;

    @Autowired
    public TokenHandlerImpl(@Value("${app.jwt.secret}") String secret,
                            UserDetailsService userDetailsService) {
        this.secret = secret;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDetails parseUserFromToken(String token) {
        final String EMAIL = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().get("email").toString();
        return userDetailsService.loadUserByUsername(EMAIL);
    }

    @Override
    public String createTokenForUser(User user) {
        final ZonedDateTime AFTERONEWEEK = ZonedDateTime.now().plusWeeks(1);

        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("password", user.getPassword())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(Date.from(AFTERONEWEEK.toInstant()))
                .compact();
    }
}
