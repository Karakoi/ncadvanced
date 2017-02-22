package com.overseer.controller;

import com.overseer.exception.entity.EntityAlreadyExistsException;
import com.overseer.model.Role;
import com.overseer.model.User;
import com.overseer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller provides api for creating, getting and deleting user.
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/getAll")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.findAll();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /**
     * Fuck javadock.
     *
     * @param id awdawd.
     * @return waddawfr.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {
        User user = userService.findOne(Long.parseLong(id));
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Create new employee.
     *
     * @param user json object which represents user.
     * @return json representation of created user.
     */
    @PostMapping("/user/register")
    public ResponseEntity<User> registerEmployee(@RequestBody User user) throws EntityAlreadyExistsException {
        Assert.notNull(user, "Create user error user is null");
        Assert.notNull(user.getEmail(), "User have no email");
        Assert.notNull(user.getFirstName(), "User must have first name");
        Assert.state(user.getRole() == Role.EMPLOYEE, "Can't register");
        userService.create(user);
        logger.info("Employee has been added with email " + user.getEmail());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Method for admin, can create user with any role.
     *
     * @param user json object which represents user.
     * @return json representation of created user.
     */
    @PostMapping("/user")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws EntityAlreadyExistsException {
        Assert.notNull(user, "Create user error user is null");
        Assert.notNull(user.getEmail(), "User have no email");
        Assert.notNull(user.getFirstName(), "User must have first name");
        userService.create(user);
        logger.info("User has been added with email " + user.getEmail());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        logger.info("User has been deleted with id " + id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
