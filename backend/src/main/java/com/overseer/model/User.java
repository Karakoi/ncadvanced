package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User entity.
 */

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class User extends AbstractEntity implements UserDetails {
    private static final long serialVersionUID = 42L;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private String phoneNumber;

    @NonNull
    private Role role; // Role easy to be mapped, because it's haven't dependency

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        switch (role) {
            case MANAGER:
                authList.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                break;
            case ADMINISTRATOR:
                authList.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
                authList.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
            default:
                authList.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }
        return authList;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
