package com.overseer.service;

import com.overseer.model.Message;

import java.util.List;

/**
 * The <code>MessageService</code> interface provide functionality
 * of private messages.
 */
public interface MessageService extends CrudService<Message, Long> {

    List<Message> findByTopic(Long topicId);

    List<Message> findDialogMessages(Long senderId, Long recipientId);

    /**
     * @param openMessage going to be encrypt.
     * @return {@link Message} with changed parameter text only.
     */
    Message encryptMessage(Message openMessage);

    /**
     * If something went wrong for instance message was'n encryption method return that message
     * from database without decryption.
     *
     * @param encryptedMessage going to be decrypt.
     * @return already decrypted {@link Message} with changed parameter text only.
     */
    Message decryptMessage(Message encryptedMessage);

    List<Message> findUnreadMessages(Long recipientId);
}
