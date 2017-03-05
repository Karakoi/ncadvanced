package com.overseer.service;

import org.springframework.mail.SimpleMailMessage;

/**
 * The <code>EmailBuilder</code> interface describes contract for
 * constructing {@link SimpleMailMessage} email message for provided entity.
 *
 * @param <T> entity type.
 */
public interface EmailBuilder<T> {

    /**
     * Constructs {@link SimpleMailMessage} for provided entity.
     *
     * @param entity an entity to construct email for.
     * @return complete {@link SimpleMailMessage} email message.
     */
    SimpleMailMessage buildMessage(T entity);
}
