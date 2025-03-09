package cz.upce.fei.nnpiacv.controller;

import cz.upce.fei.nnpiacv.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final JwtUtil jwtUtil;

    public AuthenticationController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestParam String email) {
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok(token);
    }
}
