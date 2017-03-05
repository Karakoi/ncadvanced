package com.overseer.service.impl.email;

import com.overseer.model.AbstractEntity;
import com.overseer.service.EmailBuilder;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.util.Assert;

/**
 * Basic implementation of {@link EmailBuilder} interface.
 *
 * @param <T> entity type.
 */
@PropertySource("classpath:email.properties")
public abstract class EmailBuilderImpl<T extends AbstractEntity> implements EmailBuilder<T> {
    @Value("${mail.from}")
    private String sender;

    /**
     * {@inheritDoc}.
     */
    @Override
    public SimpleMailMessage buildMessage(T entity) {
        Assert.notNull(entity, "entity must not be null");

        val message = this.buildProperties(entity);
        message.setSubject(this.getMessageSubject());
        message.setText(this.getMessageBody(entity));
        return message;
    }

    private SimpleMailMessage buildProperties(T entity) {
        val simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(this.getMessageRecipient(entity));
        simpleMailMessage.setFrom(this.sender);
        return simpleMailMessage;
    }

    abstract String getMessageRecipient(T entity);

    abstract String getMessageBody(T entity);

    abstract String getMessageSubject();
}
