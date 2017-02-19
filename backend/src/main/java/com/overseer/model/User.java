package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

/**
 * User entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class User extends AbstractEntity {

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private String secondName;

    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NonNull
    private String email;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    @NotNull
    private Role role; // Role easy to be mapped, because it's haven't dependency

}
