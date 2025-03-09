package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/users") // Používáme plural pro RESTful konvenci
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint pro získání uživatele podle ID pomocí path parametru
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByIdPath(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user); // 200 OK pokud je uživatel nalezen
        } else {
            return ResponseEntity.status(404).body(null); // 404 Not Found pokud uživatel neexistuje
        }
    }

    // Endpoint pro získání všech uživatelů nebo podle emailu
    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers(@RequestParam(required = false) String email) {
        Collection<User> users;
        if (email != null) {
            User userByEmail = userService.findUserByEmail(email);
            if (userByEmail != null) {
                users = List.of(userByEmail); // Pokud existuje uživatel s tímto emailem
            } else {
                return ResponseEntity.status(404).body(null); // 404 Not Found, pokud uživatel s emailem neexistuje
            }
        } else {
            users = userService.getAllUsers(); // Pokud není parametr email, vrátíme všechny uživatele
        }
        return ResponseEntity.ok(users); // 200 OK
    }

    // Endpoint pro přidání nového uživatele
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User createdUser = userService.addUser(user);
        return ResponseEntity.status(201).body(createdUser); // 201 Created pokud je uživatel úspěšně vytvořen
    }

    // Endpoint pro smazání uživatele podle ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Uživatel byl úspěšně smazán."); // 200 OK pokud byl uživatel smazán
        } else {
            return ResponseEntity.status(404).body("Uživatel s tímto ID neexistuje."); // 404 Not Found pokud uživatel neexistuje
        }
    }
    // Endpoint pro aktualizaci uživatele
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody @Valid User updatedUser) {
        User existingUser = userService.findUserById(id);

        if (existingUser == null) {
            return ResponseEntity.status(404).body(null);  // Pokud uživatel neexistuje
        }

        // Pokud existuje, aktualizujeme údaje
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        // Uložíme změny
        User savedUser = userService.addUser(existingUser);

        return ResponseEntity.ok(savedUser);  // Vrátíme aktualizovaného uživatele
    }
}
