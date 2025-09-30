package cz.upce.fei.nnpda_app.controller;

import cz.upce.fei.nnpda_app.config.JwtUtil;
import cz.upce.fei.nnpda_app.dto.UserRequestDto;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public record LoginRequest(String username, String password) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (userService.authenticate(request.username(), request.password())) {
            String token = jwtUtil.generateToken(request.username());
           // String expTime = jwtUtil.getExpirationTime();
            log.info("User {} logged in", request.username());
            return ResponseEntity.ok(token);
        } else {
            log.error("Invalid email or password on Login");
            return ResponseEntity.status(401).body("Neplatné přihlašovací údaje");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto request) {

        User user = userService.createUser(request);
        log.info("User {} registered", user.getEmail());
        return ResponseEntity.status(201).body(user.toResponseDto());
        }
}
