package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user") // Aktualizováno pro vrácení všech uživatelů
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

    // ✅ Vrácení všech uživatelů
    @GetMapping("/all")
    public Collection<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
