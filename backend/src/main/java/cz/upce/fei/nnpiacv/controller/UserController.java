package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.exception.EmailAlreadyExistsException;
import cz.upce.fei.nnpiacv.exception.UserNotFoundException;
import cz.upce.fei.nnpiacv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* ---------- GET /{id} ---------- */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    /* ---------- GET (all or by email) ---------- */
    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers(@RequestParam(required = false) String email) {
        if (email != null) {
            return ResponseEntity.ok(List.of(userService.findUserByEmail(email)));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /* ---------- POST ---------- */
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        return ResponseEntity.status(201).body(userService.addUser(user));
    }

    /* ---------- DELETE ---------- */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    /* ---------- PUT ---------- */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody @Valid User upd) {
        User existing = userService.findUserById(id);
        existing.setEmail(upd.getEmail());
        existing.setPassword(upd.getPassword());
        return ResponseEntity.ok(userService.addUser(existing));
    }

    /* ---------- Exception handlers ---------- */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> notFound(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> conflict(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }
}
