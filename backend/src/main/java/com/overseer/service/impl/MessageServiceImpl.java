package com.overseer.service.impl;

import com.overseer.dao.MessageDao;
import com.overseer.model.Message;
import com.overseer.service.MessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * Implementation of {@link MessageService} interface.
 */
@Service
@Slf4j
@PropertySource("cryptography.properties")
public class MessageServiceImpl extends CrudServiceImpl<Message> implements MessageService {

    private MessageDao messageDao;

    @Value("${public.key}")
    private String publicKey;

    @Value("${private.key}")
    private String privateKey;

    public MessageServiceImpl(MessageDao messageDao) {
        super(messageDao);
        this.messageDao = messageDao;
    }

    @Override
    public List<Message> findByTopic(Long topicId) {
        val list = messageDao.findByTopic(topicId);
        log.debug("Fetched messages for topic with id: {}", topicId);
        return list;
    }

    @Override
    public List<Message> findDialogMessages(Long senderId, Long recipientId) {
        val list = messageDao.findDialogMessages(senderId, recipientId);
        log.debug("Fetched messages for dialog for sender with id {} and recipient with id {}", senderId, recipientId);

        return decryptListOfMessages(list);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    @SneakyThrows
    public Message encryptMessage(Message openMessage) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] bytes = cipher.doFinal(openMessage.getText().getBytes());
        openMessage.setText(new String(Base64.getEncoder().encode(bytes)));

        return openMessage;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Message decryptMessage(Message encryptedMessage) {
        try {
            encryptedMessage.setText(decryption(encryptedMessage.getText()));
        } catch (Exception e) {
            return encryptedMessage;
        }
        return encryptedMessage;
    }

    /**
     * @param encryptedMessage going to be decrypted.
     * @return a decrypted text.
     * @throws Exception if method can't decrypt a message
     *         and that method who invoked it will return text from database.
     */
    private String decryption(String encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        PrivateKey key = generatePrivateKey();
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] cipherTextBytes = Base64.getDecoder().decode(encryptedMessage.getBytes());
        byte[] decryptedBytes = cipher.doFinal(cipherTextBytes);
        return new String(decryptedBytes);
    }

    /**
     * @param encryptedMessages is list of all messages from database.
     * @return already decrypted list of messages.
     */
    private List<Message> decryptListOfMessages(List<Message> encryptedMessages) {

        for (Message message : encryptedMessages) {
            try {
                message.setText(decryption(message.getText()));
            } catch (Exception e) {
                message.setText(message.getText());
            }
        }
        return encryptedMessages;
    }

    /**
     *  SneakyThrows because algorithm RSA is correct.
     *
     * @return already generated public key.
     * @throws InvalidKeySpecException if key in properties file incorrect.
     */
    @SneakyThrows
    private PublicKey generatePublicKey() throws InvalidKeySpecException {
        byte[] encoded = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * SneakyThrows because algorithm RSA is correct.
     *
     * @return already generated private key.
     * @throws InvalidKeySpecException if key in properties file incorrect.
     */
    @SneakyThrows
    private PrivateKey generatePrivateKey() throws InvalidKeySpecException {
        byte[] encoded = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    @Override
    public List<Message> findUnreadMessages(Long recipientId) {
        val list = messageDao.findUnreadMessages(recipientId);
        log.debug("Fetched unread messages for recipient with id {}", recipientId);
        return list;
    }
}
