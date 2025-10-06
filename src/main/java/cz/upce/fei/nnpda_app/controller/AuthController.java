package cz.upce.fei.nnpda_app.controller;

import cz.upce.fei.nnpda_app.config.JwtUtil;
import cz.upce.fei.nnpda_app.dto.User.UserChangePasswordDto;
import cz.upce.fei.nnpda_app.dto.User.UserRequestDto;
import cz.upce.fei.nnpda_app.dto.User.UserRequestPasswordDto;
import cz.upce.fei.nnpda_app.dto.User.UserRequestResetDto;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.repository.UserRepository;
import cz.upce.fei.nnpda_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;



    public record LoginRequest(String username, String password) {}

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) throws AuthenticationException {
        return userService.login(request.username(), request.password());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequestDto request) {
        User user = userService.createUser(request);
        log.info("User {} registered", user.getUsername());
        return ResponseEntity.ok(user.toResponseDto());
        }

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody UserRequestResetDto user) {
        UUID token = userService.requestPasswordReset(user);
        log.info("Password reset requested for user: {}", user.getUsername());
        return ResponseEntity.ok(token.toString());
    }

    @PostMapping ("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody UserRequestPasswordDto user) {
        userService.resetPassword(user);
        log.info("Password reset with token: {}", user.getToken());
        return ResponseEntity.ok("Heslo bylo úspěšně změněno.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserChangePasswordDto user) throws AuthenticationException {
       userService.changePassword(user);
         log.info("Password changed for user {}", user);
        return ResponseEntity.ok("Heslo bylo úspěšně změněno.");
    }

}
