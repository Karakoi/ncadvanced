package com.overseer.controller;

import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.model.Role;
import com.overseer.model.User;
import com.overseer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
     * Returns all {@link User} entities.
     *
     * @return all {@link User} entities.
     */
    @GetMapping("/user/getAll")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Returns {@link User} entity associated with provided id param.
     *
     * @param id user identifier.
     * @return {@link User} entity with http status 200 OK.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        User user = userService.findOne(Long.parseLong(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Create new employee.
     *
     * @param user json object which represents {@link User} entity.
     * @return json representation of created {@link User} entity.
     */
    @PostMapping("/user/register")
    public ResponseEntity<User> registerEmployee(@RequestBody User user) throws EntityAlreadyExistsException {
        Assert.notNull(user, "Create user error user is null");
        Assert.notNull(user.getEmail(), "User have no email");
        Assert.notNull(user.getFirstName(), "User must have first name");
        Assert.state(user.getRole() == Role.EMPLOYEE, "Can't register");
        userService.create(user);
        LOG.info("Employee has been added with email {}", user.getEmail());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Method for admin, can create {@link User} entity with any role.
     *
     * @param user json object which represents {@link User} entity.
     * @return json representation of created {@link User} entity.
     */
    @PostMapping("/user")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws EntityAlreadyExistsException {
        Assert.notNull(user, "Create user error user is null");
        Assert.notNull(user.getEmail(), "User have no email");
        Assert.notNull(user.getFirstName(), "User must have first name");
        userService.create(user);
        LOG.info("User has been added with email {}", user.getEmail());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Deletes {@link User} entity associated with provided id param.
     *
     * @param id user identifier.
     * @return http status 201 CREATED.
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        LOG.info("User has been deleted with id {}", id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Changes {@link User} password.
     *
     * @param email user's email.
     */
    @PostMapping("/user/changePassword")
    public void changePassword(@RequestBody String email) {
        userService.changePassword(email);
    }
}
