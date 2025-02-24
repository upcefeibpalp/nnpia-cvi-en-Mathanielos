package cz.upce.fei.nnpiacv.service;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Existing method to get all users
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Existing method to get user by ID
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // New method to get a user by email
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
