package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
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

    private Role role; // Role easy to be mapped, because it's haven't dependency

}
