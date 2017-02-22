package com.overseer.auth;

import com.overseer.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Repository for user data loading.
 */
@Service("userRepository")
public class UserRepositoryImpl implements UserRepository {

    private HashMap<String, User> list = new HashMap<>();

    UserRepositoryImpl() {
        list.put("admin@mail.ru", new User("name", "surname", "$2a$06$2HG3JIf8YAQ9s8wXCzm4Reel2TaMbSGsNzLfImGftLVu1SqjmX78W", "admin@mail.ru"));
        list.put("user@mail.ru", new User("name", "surname", "$2a$06$e06aVWkv0biI0tDeoiJKs.2QeRSZeS5B/v5bnHSBKzvfJHrv24jWq", "user@mail.ru"));
    }

    @Override
    public User findOneByUsername(String username) {
        return list.get(username);
    }

}
