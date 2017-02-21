package com.overseer.service.impl;

import com.overseer.dao.UserDao;
import com.overseer.exception.email.EmptyMessageException;
import com.overseer.exception.email.MessageDestinationException;
import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.exception.entity.NoSuchEntityException;
import com.overseer.model.User;
import com.overseer.service.EmailService;
import com.overseer.service.PasswordGeneratorService;
import com.overseer.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@PropertySource("classpath:email.properties")
public class UserServiceImpl implements UserService {
    public static final Logger log = Logger.getLogger(UserServiceImpl.class);

    private static final String SUBJECT_FOR_RECOVERING_PASSWORD = " Temporary password ";
    private static final String MASSAGE_FOR_EMPTY_MASSAGE_EXCEPTION = " Message for recovering password is empty ";
    private static final String MASSAGE_FOR_MESSAGE_DESTINATION_EXCEPTION = " Destination for recovering password massage is empty ";
    private static final String MASSAGE_FOR_NO_SUCH_ENTITY_EXCEPTION = " Database does not contain such user ";
    private static final String MASSAGE_FOR_ENTITY_ALREADY_EXISTS_EXCEPTION = " Database already contains such user ";

    @Value("${mail.from}")
    private String emailFrom;
    @Autowired
    private UserDao userDao;
    @Autowired
    private EmailService emailService;

    /**
     * @{inheritDoc}.
     */
    @Override
    public void changePassword(String email) throws NoSuchEntityException {
        Assert.notNull(email);
        User user = userDao.findByEmail(email);
        if (user != null) {
            String newPassword = PasswordGeneratorService.generatePassword();
            user.setPassword(newPassword);
            userDao.save(user);
            try {
                emailService.sendMessage(createMailMessage(user));
            } catch (EmptyMessageException e) {
                log.error(MASSAGE_FOR_EMPTY_MASSAGE_EXCEPTION, e);
            } catch (MessageDestinationException e) {
                log.error(MASSAGE_FOR_MESSAGE_DESTINATION_EXCEPTION, e);
            }
        } else {
            throw new NoSuchEntityException(MASSAGE_FOR_NO_SUCH_ENTITY_EXCEPTION);
        }
    }

    /**
     * Forms message for recovering password.
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
                + "Your temporary password is \n"
                + user.getPassword()
                + "\n");
        return  mailMessage;
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public User create(User user) throws EntityAlreadyExistsException {
        Assert.notNull(user);
        boolean isExist = userDao.exists(user.getId());
        if (isExist) {
            throw new EntityAlreadyExistsException(MASSAGE_FOR_ENTITY_ALREADY_EXISTS_EXCEPTION);
        }
        return userDao.save(user);
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public User update(User user) throws NoSuchEntityException {
        Assert.notNull(user);
        boolean isExist = userDao.exists(user.getId());
        if (!isExist) {
            throw new NoSuchEntityException(MASSAGE_FOR_NO_SUCH_ENTITY_EXCEPTION);
        }
        return userDao.save(user);
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public User findOne(Long id) {
        Assert.notNull(id);
        return userDao.findOne(id);
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public void delete(User user) {
        Assert.notNull(user);
        userDao.delete(user);
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id);
        userDao.delete(id);
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        Assert.notNull(id);
        return userDao.exists(id);
    }

    /**
     * @{inheritDoc}.
     */
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
