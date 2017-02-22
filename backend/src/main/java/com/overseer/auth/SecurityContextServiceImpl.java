package com.overseer.auth;

import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.User;
import com.overseer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Represents service for current user obtaining.
 */
@Service
public class SecurityContextServiceImpl implements SecurityContextService {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityContextServiceImpl.class);
    private final UserService userService;

    @Autowired
    public SecurityContextServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User currentUser() {
        final Authentication AUTHENTICATION = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        try {
            user = userService.findByEmail(AUTHENTICATION.getName());
        } catch (NoSuchEntityException e) {
            LOG.warn("NoSuchEntityException in currentUser() :" + e);
        }
        return user;
    }
}
