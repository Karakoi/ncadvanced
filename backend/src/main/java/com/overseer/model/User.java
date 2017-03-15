package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Email;

import java.time.LocalDate;
import javax.validation.constraints.*;


/**
 * User entity.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("PMD.UnusedPrivateField")
public class User extends AbstractEntity {
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 40;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PHONE_LENGTH = 20;

    @NotNull(message = "User have to have first name")
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH, message = "Size of first name has to be between 3 and 30")
    private String firstName;

    @NotNull(message = "User have to have last name")
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH, message = "Size of last name has to be between 3 and 30")
    private String lastName;

    @Size(max = MAX_NAME_LENGTH, message = "Size of first name has to be between 3 and 40")
    private String secondName;

    @NotNull(message = "User has to have password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "User have to have email")
    @Email
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Size(max = MAX_PHONE_LENGTH, message = "Max length of phone number is 20")
    private String phoneNumber;

    @NotNull(message = "User have to have role")
    private Role role;

    private Boolean isDeactivated;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateOfDeactivation;


}
