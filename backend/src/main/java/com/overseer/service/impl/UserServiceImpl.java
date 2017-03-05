package com.overseer.service.impl;


import com.overseer.dao.UserDao;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.Role;
import com.overseer.model.User;
import com.overseer.service.EmailService;
import com.overseer.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Implementation of {@link UserService} interface.
 */
@Service
@PropertySources({
        @PropertySource("classpath:email.properties"),
        @PropertySource("classpath:security.properties")
})
public class UserServiceImpl extends CrudServiceImpl<User> implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String SUBJECT_FOR_RECOVERING_PASSWORD = " New password ";
    private static final short DEFAULT_PAGE_SIZE = 20;

    @Value("${mail.from}")
    private String emailFrom;
    @Value("${password.length}")
    private Integer newPasswordLength;

    private EmailService emailService;
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao, EmailService emailService) {
        super(userDao);
        this.userDao = userDao;
        this.emailService = emailService;
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
        userDao.save(user);
        SimpleMailMessage message = this.createMailMessage(user, newPassword);
        emailService.sendMessage(message);
    }

    /**
     * Forms message for recovering password.
     *
     * @param user user to recover password for, must not be {@literal null}
     * @return message for recovering password
     */
    private SimpleMailMessage createMailMessage(User user, String newPassword) {
        Assert.notNull(user, "user must not be null");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom(emailFrom);
        mailMessage.setSubject(SUBJECT_FOR_RECOVERING_PASSWORD);
        mailMessage.setText("Dear "
                + user.getFirstName()
                + " "
                + user.getLastName()
                + ",\n\n"
                + "You or someone else requested a password recovery to "
                + user.getEmail()
                + " account.\n"
                + "Your new password is \n"
                + newPassword
                + "\n");
        return mailMessage;
    }

    @Override
    public User findByEmail(String email) throws NoSuchEntityException {
        Assert.notNull(email, "email must not be null");
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NoSuchEntityException("Failed to retrieve user with email " + email);
        }
        LOG.debug("Retrieving user with email: {}", email);
        return user;
    }

    @Override
    public List<User> findByRole(Role role, int pageNumber) {
        Assert.notNull(role, "role must not be null");
        LOG.debug("Retrieving user with role: {}", role);
        return userDao.findByRole(role, DEFAULT_PAGE_SIZE, pageNumber);
    }
}
