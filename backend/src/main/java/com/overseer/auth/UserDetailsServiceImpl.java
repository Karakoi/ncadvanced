package com.overseer.auth;

import com.overseer.model.User;
import com.overseer.service.UserService;
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
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findByEmail(login);
        AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        if (user == null) {
            throw new UsernameNotFoundException("User with login: " + login + " is not found.");
        }
        detailsChecker.check(user);
        return user;
    }
}
