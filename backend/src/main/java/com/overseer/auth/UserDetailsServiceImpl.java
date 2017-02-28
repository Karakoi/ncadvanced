package com.overseer.auth;

import com.overseer.model.Role;
import com.overseer.model.User;
import com.overseer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

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
        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        getAuthorities(user.getRole()));
        detailsChecker.check(userDetails);
        return userDetails;
    }

    /**
     * Returns a list of user authorities.
     * @param role user Role
     * @return List of user authorities.
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        switch (role.getName()) {
            case "admin":
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
                break;
            case "office manager":
                authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                break;
            default:
                authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }
        return authorities;
    }
}
