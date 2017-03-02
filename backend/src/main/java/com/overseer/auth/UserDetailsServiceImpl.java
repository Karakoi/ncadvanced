package com.overseer.auth;

import com.overseer.dao.UserDao;
import com.overseer.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Represents service for user-specific data loading.
 */
@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.findByEmail(login);
        AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        if (user == null) {
            throw new UsernameNotFoundException("User with login: " + login + " is not found.");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        detailsChecker.check(jwtUser);
        return jwtUser;
    }
}
