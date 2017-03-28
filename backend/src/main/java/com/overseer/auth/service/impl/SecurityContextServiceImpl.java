package com.overseer.auth.service.impl;

import com.overseer.auth.service.SecurityContextService;
import com.overseer.dao.UserDao;
import com.overseer.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * {@inheritDoc}.
 */
@Service("securityContextService")
@RequiredArgsConstructor
public class SecurityContextServiceImpl implements SecurityContextService {
    private final UserDao userDao;

    /**
     * {@inheritDoc}.
     */
    @Override
    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userDao.findByEmail(authentication.getName());
    }
}
