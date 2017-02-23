package com.overseer.auth;

import com.overseer.model.User;
import com.overseer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Represents service for current user obtaining.
 */
@Service
@RequiredArgsConstructor
public class SecurityContextServiceImpl implements SecurityContextService {
    private final UserService userService;

    @Override
    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(authentication.getName());
    }
}
