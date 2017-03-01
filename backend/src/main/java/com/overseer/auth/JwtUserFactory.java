package com.overseer.auth;

import com.overseer.model.User;

/**
 * Factory for creating {@link JwtUser} from {@link User} entity.
 */
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    /**
     * Creates {@link JwtUser} entity from {@link User} entity.
     *
     * @param user {@link User} entity.
     * @return {@link JwtUser} entity.
     */
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getSecondName(),
                user.getPassword(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getPhoneNumber(),
                user.getRole()
        );
    }
}
