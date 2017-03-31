package com.overseer.controller;


import com.overseer.dto.RequestSubscriberDTO;
import com.overseer.model.User;
import com.overseer.service.RequestSubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provide api to subscribing on request events for getting notifications about changing status.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
public class RequestSubscribeController {

    private final RequestSubscribeService requestSubscribeService;


    /**
     * Simple mechanism for subscribing/unsubscribing.
     *
     * @param requestSubscriber user id which subscribe on request (via request id)
     * @return status of subscribing. True - subscribed, false - not subscribed.
     */
    @PostMapping
    public ResponseEntity<Boolean> subscribe(@RequestBody RequestSubscriberDTO requestSubscriber) {
        if (requestSubscribeService.exist(requestSubscriber.getSubscriberId(), requestSubscriber.getRequestId())) {
            requestSubscribeService.delete(requestSubscriber.getSubscriberId(), requestSubscriber.getRequestId());
        } else {
            requestSubscribeService.save(requestSubscriber.getSubscriberId(), requestSubscriber.getRequestId());
        }
        return new ResponseEntity<>(requestSubscribeService
                .exist(requestSubscriber.getSubscriberId(), requestSubscriber.getRequestId()), HttpStatus.OK);
    }


    @PostMapping("/check")
    public ResponseEntity<Boolean> check(@RequestBody RequestSubscriberDTO requestSubscriber) {
        return new ResponseEntity<>(requestSubscribeService
                .exist(requestSubscriber.getSubscriberId(), requestSubscriber.getRequestId()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getSubscribers(@RequestParam Long requestId) {
        return new ResponseEntity<>(requestSubscribeService.getSubscribersOfRequest(requestId), HttpStatus.OK);
    }
}
