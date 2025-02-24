package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Endpoint s Query parametrem: /user?id=1
    @GetMapping
    public User getUserById(@RequestParam Long id) {
        return userService.findUserById(id);
    }

    // ✅ Endpoint s Path parametrem: /user/1
    @GetMapping("/{id}")
    public User getUserByIdPath(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    // ✅ Vrácení všech uživatelů, nebo podle emailu
    @GetMapping("/all")
    public Collection<User> getAllUsers(@RequestParam(required = false) String email) {
        if (email != null) {
            // Return the user by email if the parameter is provided
            return List.of(userService.findUserByEmail(email));
        } else {
            // Return all users if no email is provided
            return userService.getAllUsers();
        }
    }
}
