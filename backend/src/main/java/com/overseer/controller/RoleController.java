package com.overseer.controller;

import com.overseer.model.Role;
import com.overseer.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller provides api for creating, getting and deleting role.
 */
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;

    /**
     * Method for admin, can create {@link Role} entity.
     *
     * @param role json object which represents {@link Role} entity.
     * @return json representation of created {@link Role} entity.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role saveRole = roleService.create(role);
        LOG.debug("Saved role with id: {}", saveRole.getId());
        return new ResponseEntity<>(saveRole, HttpStatus.CREATED);
    }

    /**
     * Deletes {@link Role} entity associated with provided id param.
     *
     * @param id role identifier.
     * @return http status 201 CREATED.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Updates {@link Role} entity associated with provided id param.
     *
     * @param role to update.
     * @return http status 201 CREATED.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        Role updatedRole = roleService.update(role);
        LOG.debug("Updated role with id: {}", updatedRole.getId());
        return new ResponseEntity<>(updatedRole, HttpStatus.CREATED);
    }


    /**
     * Returns all {@link Role} entities.
     *
     * @return all {@link Role} entities.
     */
    @GetMapping()
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}
