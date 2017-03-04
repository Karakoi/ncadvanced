package com.overseer.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.overseer.model.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Security based wrapper for {@link com.overseer.model.User} entity.
 */
@Data
@SuppressWarnings("PMD.UnusedPrivateField")
public class JwtUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String secondName;
    private final String password;
    private final String email;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    private final Role role;

    @JsonIgnore
    public Long getId() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        switch (role.getName()) {
            case "office manager":
                authList.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
                break;
            case "admin":
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
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
