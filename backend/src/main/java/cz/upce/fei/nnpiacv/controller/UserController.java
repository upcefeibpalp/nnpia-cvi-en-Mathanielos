package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserResponseDto;
import cz.upce.fei.nnpiacv.exception.EmailAlreadyExistsException;
import cz.upce.fei.nnpiacv.exception.UserNotFoundException;
import cz.upce.fei.nnpiacv.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")   // uprav podle portu tvého dev-serveru
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // --- helper pro převod entita → DTO ---
    private UserResponseDto toDto(User u) {
        return new UserResponseDto(
                u.getId(),
                u.getEmail(),
                u.getPassword(),
                u.getVersion(),
                u.getProfile(),
                u.isActive()
        );
    }

    /* ---------- GET /{id} ---------- */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        User u = userService.findUserById(id);
        return ResponseEntity.ok(toDto(u));
    }

    /* ---------- GET (all or by email) ---------- */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(@RequestParam(required = false) String email) {
        List<User> list = (email != null)
                ? List.of(userService.findUserByEmail(email))
                : userService.getAllUsers().stream().toList();

        List<UserResponseDto> dtos = list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /* ---------- POST ---------- */
    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody User newUser) {
        User created = userService.addUser(newUser);
        // vrátíme 201 + Location header
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + created.getId()))
                .body(toDto(created));
    }

    /* ---------- PUT ---------- */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User updUser
    ) {
        User existing = userService.findUserById(id);
        existing.setEmail(updUser.getEmail());
        existing.setPassword(updUser.getPassword());
        existing.setActive(updUser.isActive());
        User saved = userService.addUser(existing);
        return ResponseEntity.ok(toDto(saved));
    }

    /* ---------- DELETE ---------- */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    /* ---------- Exception handlers ---------- */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleConflict(EmailAlreadyExistsException ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }
    /* ---------- POST /{id}/activate ---------- */
    @PostMapping("/{id}/activate")
    public ResponseEntity<UserResponseDto> activateUser(@PathVariable Long id) {
        User updated = userService.activateUser(id);
        return ResponseEntity.ok(toDto(updated));
    }

    /* ---------- POST /{id}/deactivate ---------- */
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<UserResponseDto> deactivateUser(@PathVariable Long id) {
        User updated = userService.deactivateUser(id);
        return ResponseEntity.ok(toDto(updated));
    }

}
