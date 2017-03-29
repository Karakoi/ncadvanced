package com.overseer.service;

import com.overseer.model.User;

import java.util.List;

/**
 * Provide service to subscribing/unsubscriping on requests changing events.
 */
public interface RequestSubscribeService {

    boolean exist(Long requestId, Long subscriberId);

    void save(Long requestId, Long subscriberId);

    void delete(Long requestId, Long subscriberId);

    List<User> getSubscribersOfRequest(Long requestId);
}
