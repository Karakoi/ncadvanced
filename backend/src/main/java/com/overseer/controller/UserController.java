package com.overseer.controller;

import com.overseer.dto.UserSearchDTO;
import com.overseer.model.User;
import com.overseer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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

    private final UserService userService;

    /**
     * Returns {@link User} entity associated with provided id param.
     *
     * @param id user identifier.
     * @return {@link User} entity with http status 200 OK.
     */
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<User> fetchUser(@PathVariable Long id) {
        User user = userService.findOne(id);
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
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.update(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
    }

    /**
     * Changes {@link User} password.
     *
     * @param recoverInfo user's recover params.
     */
    @PostMapping(value = "/changePassword")
    public void changePassword(@RequestBody RecoverInfo recoverInfo) {
        userService.changePassword(recoverInfo.email);
    }

    /**
     * Returns all {@link User} entities.
     *
     * @return all {@link User} entities.
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(@RequestParam int page, @RequestParam int size) {
        List<User> users = userService.fetchPage(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Gets all deactivated {@link User} entities.
     *
     * @return all deactivated {@link User} entities with http status 200 OK.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deactivated")
    public ResponseEntity<List<User>> getAllDeactivatedUsers(@RequestParam int page, @RequestParam int size) {
        List<User> users = userService.findAllDeactivated(page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Activates {@link User} entity associated with provided id param.
     *
     * @param id user identifier.
     * @return http status 200 OK.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/activate/{id}")
    public ResponseEntity activateUser(@PathVariable Long id) {
        userService.activate(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pageDeactivatedUsersCount")
    public ResponseEntity<Long> getPageDeactivatedUsersCount() {
        long pageCount = userService.getCountAllDeactivated();
        return new ResponseEntity<>(pageCount, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    @GetMapping("/pageCount")
    public ResponseEntity<Long> getPageCount() {
        long pageCount = userService.getCount();
        return new ResponseEntity<>(pageCount, HttpStatus.OK);
    }

    /**
     * Recover request.
     */
    @Value
    private static final class RecoverInfo {
        private final String email;
    }

    /**
     * Returns list of filtered users by specified search params in {@link UserSearchDTO} object.
     *
     * @param searchDTO search params dto object
     * @return {@link User} list with http status 200 OK..
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchRequests(UserSearchDTO searchDTO) {
        List<User> users = userService.searchUsers(searchDTO);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Returns list of specified user chat partners.
     * Partners - users users who sent a message or which user sent.
     *
     * @param userId specified user
     * @return list of chat partners
     */
    @GetMapping("/findUserChatPartners")
    public ResponseEntity<List<User>> findUserChatPartners(@RequestParam Long userId) {
        List<User> users = userService.findUserChatPartners(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Returns list of users which have unread messages.
     *
     * @param userId specified user
     * @return list of users which have unread messages
     */
    @GetMapping("/findUsersWithUnreadMessages")
    public ResponseEntity<List<User>> findUsersWithUnreadMessages(@RequestParam Long userId) {
        List<User> users = userService.findUsersWithUnreadMessages(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
