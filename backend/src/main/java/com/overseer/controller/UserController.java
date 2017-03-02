package com.overseer.controller;

import com.overseer.model.User;
import com.overseer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller provides api for creating, getting and deleting user.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Returns {@link User} entity associated with provided id param.
     *
     * @param id user identifier.
     * @return {@link User} entity with http status 200 OK.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findOne(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Method for admin, can create {@link User} entity with any role.
     *
     * @param user json object which represents {@link User} entity.
     * @return json representation of created {@link User} entity.
     */
    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User saveUser = userService.create(user);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    /**
     * Updates {@link User} entity associated with provided id param.
     *
     * @param user user to update.
     * @return http status 201 CREATED.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.update(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    /**
     * Deletes {@link User} entity associated with provided id param.
     *
     * @param id user identifier.
     * @return http status 201 CREATED.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Changes {@link User} password.
     *
     * @param recoverInfo user's recover params.
     */
    @PostMapping(value = "/users/changePassword")
    public void changePassword(@RequestBody RecoverInfo recoverInfo) {
        LOG.debug("Sending recover info to: {}", recoverInfo.email);
        userService.changePassword(recoverInfo.email);
    }

    /**
     * Returns all {@link User} entities.
     *
     * @return all {@link User} entities.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Recover request.
     */
    @Value
    private static final class RecoverInfo {
        private final String email;
    }
}