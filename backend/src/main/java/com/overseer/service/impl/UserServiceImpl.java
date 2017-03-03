package com.overseer.service.impl;

import com.overseer.dao.UserDao;
import com.overseer.exception.UserAlreadyExistsException;
import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.Role;
import com.overseer.model.User;
import com.overseer.model.modelEnum.MessageReason;
import com.overseer.service.EmailService;
import com.overseer.service.MessageBuilder;
import com.overseer.service.UserService;
import com.overseer.util.PasswordGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Implementation of {@link UserService} interface.
 */
@Service
@PropertySource("classpath:email.properties")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String SUBJECT_FOR_RECOVERING_PASSWORD = " New password ";

    @Value("${mail.from}")
    private String emailFrom;

    private final UserDao userDao;
    private final EmailService emailService;

    /**
     * {@inheritDoc}.
     */
    @Override
    public User create(User user) {
        Assert.notNull(user);
        if (user.getId() != null) {
            throw new EntityAlreadyExistsException("Failed to create user. Id was not null for user: " + user);
        }
        LOG.debug("Saving user with email: {}", user.getEmail());
        if (userDao.findByEmail(user.getEmail()) == null) {
            return userDao.save(user);
        } else {
            throw new UserAlreadyExistsException("User with such email already exists");
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User update(User user) {
        Assert.notNull(user);
        if (user.getId() == null) {
            throw new NoSuchEntityException("Failed to update user. Id was null for user: " + user);
        }
        LOG.debug("Updating user with email: {}", user.getEmail());
        return userDao.save(user);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User findOne(Long id) {
        Assert.notNull(id);
        User user = userDao.findOne(id);
        if (user == null) {
            throw new NoSuchEntityException("Failed to retrieve user with id " + id);
        }
        LOG.debug("Retrieving user with id: {}", id);
        return user;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(User user) {
        Assert.notNull(user);
        LOG.debug("Removing user with email: {}", user.getEmail());
        userDao.delete(user);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        LOG.debug("Removing user with id: {}", id);
        userDao.delete(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        Assert.notNull(id);
        LOG.debug("Checking if user with id: {} exists", id);
        return userDao.exists(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findAll() {
        LOG.info("Retrieving all users...");
        return userDao.findAll();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void changePassword(String email) throws NoSuchEntityException {
        Assert.notNull(email);
        User user = this.findByEmail(email);
        PasswordGeneratorUtil passwordGeneratorUtil = new PasswordGeneratorUtil();
        String newPassword = passwordGeneratorUtil.generatePassword();
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
        Assert.notNull(user);


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

    // test method
    private SimpleMailMessage forgotPassword(User user) {
        val messageBuilder = new MessageBuilderImpl();
        return messageBuilder.builderMessage(user, MessageReason.FORGOT_PASSWORD);
    }

    @Override
    public User findByEmail(String email) throws NoSuchEntityException {
        Assert.notNull(email);
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NoSuchEntityException("Failed to retrieve user with email " + email);
        }
        LOG.debug("Retrieving user with email: {}", email);
        return user;
    }

    @Override
    public List<User> findByRole(Role role) {
        Assert.notNull(role);
        LOG.debug("Retrieving user with role: {}", role);
        return userDao.findByRole(role);
    }
}
