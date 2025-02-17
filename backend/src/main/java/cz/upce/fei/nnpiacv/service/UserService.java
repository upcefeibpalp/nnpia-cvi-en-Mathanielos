package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final Map<Long, User> users = new HashMap<>();

    @PostConstruct
    public void initUsers() {
        users.put(1L, new User(1L, "tomas@example.com", "securePassword"));
        users.put(2L, new User(2L, "ondrej@example.com", "securePassword"));

        logger.info("Users initialized: {}", users);
    }

    // ✅ Vyhledání uživatele podle ID
    public User findUserById(Long id) {
        User user = users.get(id);
        if (user != null) {
            logger.info("User found: {}", user);
        } else {
            logger.warn("User with ID {} not found!", id);
        }
        return user;
    }

    // ✅ Vrácení všech uživatelů
    public Collection<User> getAllUsers() {
        return users.values();
    }
}
