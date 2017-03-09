package com.overseer.controller;

import com.overseer.model.Role;
import com.overseer.model.User;
import com.overseer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller provides api for creating, getting and deleting user.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private static final Long DEFAULT_PAGE_SIZE = 20L;

    private final UserService userService;

    /**
     * Returns {@link User} entity associated with provided id param.
     *
     * @param id user identifier.
     * @return {@link User} entity with http status 200 OK.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN','MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<User> fetchUser(@PathVariable Long id) {
        User user = userService.findOne(id);
        LOG.debug("Fetching user with id: {}", id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Method for admin, can create {@link User} entity with any role.
     *
     * @param user json object which represents {@link User} entity.
     * @return json representation of created {@link User} entity.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saveUser = userService.create(user);
        LOG.debug("Saved user with id: {}", saveUser.getId());
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    /**
     * Deletes {@link User} entity associated with provided id param.
     *
     * @param id user identifier.
     * @return http status 201 CREATED.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Updates {@link User} entity associated with provided id param.
     *
     * @param user user to update.
     * @return http status 201 CREATED.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.update(user);
        LOG.debug("Updated user with id: {}", updatedUser.getId());
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    /**
     * Changes {@link User} password.
     *
     * @param recoverInfo user's recover params.
     */
    @PostMapping(value = "/changePassword")
    public void changePassword(@RequestBody RecoverInfo recoverInfo) {
        LOG.debug("Sending recover info to: {}", recoverInfo.email);
        userService.changePassword(recoverInfo.email);
    }

    /**
     * Returns all {@link User} entities.
     *
     * @return all {@link User} entities.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<User>> getAllUser(@RequestParam int page) {
        List<User> users = userService.fetchPage(page);
        LOG.debug("Fetched {} users for page: {}", users.size(), page);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Returns all {@link Role} entities.
     *
     * @return all {@link Role} entities.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = userService.findAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/pageCount")
    public ResponseEntity<Long> getPageCount() {
        long pageCount = userService.getCount() / DEFAULT_PAGE_SIZE + 1;
        return new ResponseEntity<>(pageCount, HttpStatus.OK);
    }

    /**
     * Recover request.
     */
    @Value
    private static final class RecoverInfo {
        private final String email;
    }
}
