package com.overseer.service.impl;

import com.overseer.dao.UserDao;
import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.Role;
import com.overseer.model.User;
import com.overseer.service.EmailBuilder;
import com.overseer.service.EmailService;
import com.overseer.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Implementation of {@link UserService} interface.
 */
@Service
@PropertySource("classpath:security.properties")
public class UserServiceImpl extends CrudServiceImpl<User> implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final short DEFAULT_PAGE_SIZE = 20;

    @Value("${password.length}")
    private Integer newPasswordLength;

    private EmailService emailService;
    private EmailBuilder<User> emailStrategy;
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao,
                           EmailService emailService,
                           @Qualifier("recoverBuilderImpl") EmailBuilder<User> emailStrategy) {
        super(userDao);
        this.userDao = userDao;
        this.emailService = emailService;
        this.emailStrategy = emailStrategy;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User create(User user) throws EntityAlreadyExistsException {
        Assert.notNull(user, "user must not be null");
        if (!this.emailAvailable(user)) {
            throw new EntityAlreadyExistsException("Supplied email is already taken: " + user.getEmail());
        }
        return super.create(user);
    }

    /**
     * Checks if supplied email is already in the database.
     *
     * @param user user to check email for.
     * @return true if email available, false otherwise.
     */
    private boolean emailAvailable(User user) {
        String email = user.getEmail();
        return this.findByEmail(email) == null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void changePassword(String email) throws NoSuchEntityException {
        Assert.notNull(email, "email must not be null");
        User user = this.findByEmail(email);
        String newPassword = RandomStringUtils.randomAlphanumeric(newPasswordLength);
        user.setPassword(newPassword);
        SimpleMailMessage message = this.emailStrategy.buildMessage(user);
        emailService.sendMessage(message);
        userDao.save(user);
    }

    @Override
    public User findByEmail(String email) throws NoSuchEntityException {
        Assert.notNull(email, "email must not be null");
        User user = userDao.findByEmail(email);
        LOG.debug("Retrieving user with email: {}", email);
        return user;
    }

    @Override
    public List<User> findByRole(Role role, int pageNumber) {
        Assert.notNull(role, "role must not be null");
        LOG.debug("Retrieving user with role: {}", role);
        return userDao.findByRole(role, DEFAULT_PAGE_SIZE, pageNumber);
    }

    @Override
    public List<User> findManagersByEmployee(Long employeeId) {
        LOG.debug("Retrieving Managers by employee with employeeId: {}", employeeId);
        return userDao.findManagersByEmployee(employeeId);
    }

    @Override
    public List<User> findUsersByManager(Long managerId) {
        LOG.debug("Retrieving employees by manager with managerId: {}", managerId);
        return userDao.findUsersByManager(managerId);
    }
}
