package com.overseer.auth;

import com.overseer.model.User;
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

    private final UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findOneByUsername(login);
        UserDetails userDetails;
        if (user != null) {
            userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user.getEmail()));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return userDetails;
    }

    /**
     * Returns user roles list.
     * @param role User role
     * @return List of user granted roles.
     */
    private List<SimpleGrantedAuthority> getAuthorities(String role) {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        if ("admin@mail.ru".equals(role)) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return authList;
    }
}
