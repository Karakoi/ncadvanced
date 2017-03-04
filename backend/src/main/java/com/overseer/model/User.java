package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import javax.validation.constraints.*;


/**
 * User entity.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("PMD.UnusedPrivateField")
public class User extends AbstractEntity {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 40;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PHONE_LENGTH = 20;

    @NotNull(message = "User have to have first name")
    @NonNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH, message = "Size of first name has to be between 3 and 30")
    private String firstName;

    @NotNull(message = "User have to have last name")
    @NonNull
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH, message = "Size of last name has to be between 3 and 30")
    private String lastName;

    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH, message = "Size of first name has to be between 3 and 40")
    private String secondName;

    @NotNull(message = "User have to have password")
    @NonNull
    @Min(MIN_PASSWORD_LENGTH)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "User have to have email")
    @NonNull
    @Pattern(regexp = "^(?:[a-zA-Z0-9_'^&/+-])+(?:\\.(?:[a-zA-Z0-9_'^&/+-])+) *@(?:(?:\\[?(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))\\           .){3}(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\]?)|(?:[a-zA-Z0-9-]+\\.)+(?:[a-zA-Z]){2,}\\.?)$",
            message = "Incorrect email")
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Max(value = MAX_PHONE_LENGTH, message = "Max length of phone number is 20")
    private String phoneNumber;

    @NotNull(message = "User have to have role")
    @NonNull
    private Role role;

}
