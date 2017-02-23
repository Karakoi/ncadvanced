package com.overseer.auth;

import com.overseer.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Represents the service for working with tokens.
 */
@Component("tokenHandler")
@PropertySource("classpath:security.properties")
public class TokenHandlerImpl implements TokenHandler {

    private final String secret;
    private final UserDetailsService userDetailsService;

    public TokenHandlerImpl(@Value("${app.jwt.secret}") String secret,
                            UserDetailsService userDetailsService) {
        this.secret = secret;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public UserDetails parseUserFromToken(String token) {
        String email = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("email")
                .toString();
        return userDetailsService.loadUserByUsername(email);
    }

    @Override
    public String createTokenForUser(User user) {
        final ZonedDateTime AFTERONEWEEK = ZonedDateTime.now().plusWeeks(1);

        return Jwts.builder()
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(Date.from(AFTERONEWEEK.toInstant()))
                .compact();
    }
}
