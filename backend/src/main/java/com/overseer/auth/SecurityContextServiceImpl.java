package com.overseer.auth;

import com.overseer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Represents service for current user obtaining.
 */
@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    private final UserRepository userRepository;

    @Autowired
    public SecurityContextServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User currentUser() {
        final Authentication AUTHENTICATION = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findOneByUsername(AUTHENTICATION.getName());
    }
}
