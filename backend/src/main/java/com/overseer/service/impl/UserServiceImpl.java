package com.overseer.service.impl;

import com.overseer.dao.UserDao;
import com.overseer.exception.email.EmptyMessageException;
import com.overseer.exception.email.MessageDestinationException;
import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.User;
import com.overseer.service.EmailService;
import com.overseer.service.UserService;
import com.overseer.util.PasswordGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Implementation of {@link UserService} interface.
 */
@Service
@PropertySource("classpath:email.properties")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private static final String SUBJECT_FOR_RECOVERING_PASSWORD = " New password ";
    private static final String EMPTY_MESSAGE_EXCEPTION_MESSAGE = " Message for recovering password is empty ";
    private static final String DESTINATION_EXCEPTION_MESSAGE = " Destination for recovering password massage is empty ";
    private static final String NO_SUCH_ENTITY_MESSAGE = " Database does not contain such user ";
    private static final String ENTITY_ALREADY_EXISTS_MESSAGE = " Database already contains such user ";

    @Value("${mail.from}")
    private String emailFrom;
    private final UserDao userDao;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    /**
     * {@inheritDoc}.
     */
    @Override
    public User create(User user) throws EntityAlreadyExistsException {
        Assert.notNull(user);
        boolean isExist = userDao.exists(user.getId());
        if (isExist) {
            throw new EntityAlreadyExistsException(ENTITY_ALREADY_EXISTS_MESSAGE);
        }
        return userDao.save(user);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User update(User user) throws NoSuchEntityException {
        Assert.notNull(user);
        boolean isExist = userDao.exists(user.getId());
        if (!isExist) {
            throw new NoSuchEntityException(NO_SUCH_ENTITY_MESSAGE);
        }
        return userDao.save(user);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public User findOne(Long id) throws NoSuchEntityException {
        Assert.notNull(id);
        User user = userDao.findOne(id);
        if (user == null) {
            throw new NoSuchEntityException(NO_SUCH_ENTITY_MESSAGE);
        }
        return user;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(User user) {
        Assert.notNull(user);
        userDao.delete(user);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        userDao.delete(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        Assert.notNull(id);
        return userDao.exists(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void changePassword(String email) throws NoSuchEntityException {
        Assert.notNull(email);
        User user = userDao.findByEmail(email);
        if (user != null) {
            PasswordGeneratorUtil passwordGeneratorUtil = new PasswordGeneratorUtil();
            String newPassword = passwordGeneratorUtil.generatePassword();
            user.setPassword(newPassword);
            userDao.save(user);
            try {
                emailService.sendMessage(this.createMailMessage(user));
            } catch (EmptyMessageException e) {
                LOG.error(EMPTY_MESSAGE_EXCEPTION_MESSAGE, e);
            } catch (MessageDestinationException e) {
                LOG.error(DESTINATION_EXCEPTION_MESSAGE, e);
            }
        } else {
            throw new NoSuchEntityException(NO_SUCH_ENTITY_MESSAGE);
        }
    }

    /**
     * Forms message for recovering password.
     *
     * @param user user to recover password for, must not be {@literal null}
     * @return message for recovering password
     */
    private SimpleMailMessage createMailMessage(User user) {
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
                + user.getPassword()
                + "\n");
        return mailMessage;
    }

    @Override
    public User findByEmail(String email) throws NoSuchEntityException {
        Assert.notNull(email);
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new NoSuchEntityException(NO_SUCH_ENTITY_MESSAGE);
        }
        return user;
    }
}
