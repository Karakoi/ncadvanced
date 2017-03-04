package com.overseer.auth.service.impl;

import com.overseer.auth.JwtUserFactory;
import com.overseer.auth.service.TokenHandler;
import com.overseer.dao.UserDao;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * {@inheritDoc}.
 */
@Component("tokenHandler")
@PropertySource("classpath:security.properties")
@RequiredArgsConstructor
public class TokenHandlerImpl implements TokenHandler {

    @Value("${app.jwt.secret}")
    private String secret;
    private final UserDao userDao;

    /**
     * {@inheritDoc}.
     */
    @Override
    public UserDetails parseUserFromToken(String token) {
        String userId = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
        Long id = Long.parseLong(userId);
        User user = userDao.findOne(id);
        if (user == null) {
            throw new NoSuchEntityException("Failed to retrieve user with id: " + id);
        }
        return JwtUserFactory.create(user);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String createTokenForUser(User user) {
        ZonedDateTime afterOneWeek = ZonedDateTime.now().plusWeeks(1);

        return Jwts.builder()
                .claim("id", user.getId())
                .claim("role", user.getRole().getName())
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(Date.from(afterOneWeek.toInstant()))
                .compact();
    }
}
