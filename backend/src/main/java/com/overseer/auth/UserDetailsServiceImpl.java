package com.overseer.auth;

import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.Role;
import com.overseer.model.User;
import com.overseer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents service for user-specific data loading.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserService userService;

    @Autowired
    UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userService.findByEmail(login);
        } catch (NoSuchEntityException e) {
            LOG.warn("NoSuchEntityException in loadUserByUsername() :" + e);
        }
        UserDetails userDetails;
        if (user != null) {
            userDetails = new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    getAuthorities(user.getRole()));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return userDetails;
    }

    /**
     * Returns user roles list.
     *
     * @param role User role
     * @return List of user granted roles.
     */
    private List<SimpleGrantedAuthority> getAuthorities(Role role) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        switch (role) {
            case MANAGER:
                authList.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                break;
            case ADMINISTRATOR:
                authList.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
                authList.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            default:
                authList.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }
        return authList;
    }
}
