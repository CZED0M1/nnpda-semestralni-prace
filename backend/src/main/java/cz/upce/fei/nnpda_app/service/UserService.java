package cz.upce.fei.nnpda_app.service;

import cz.upce.fei.nnpda_app.config.JwtUtil;
import cz.upce.fei.nnpda_app.dto.User.UserChangePasswordDto;
import cz.upce.fei.nnpda_app.dto.User.UserRequestDto;
import cz.upce.fei.nnpda_app.dto.User.UserRequestPasswordDto;
import cz.upce.fei.nnpda_app.dto.User.UserRequestResetDto;
import cz.upce.fei.nnpda_app.exception.NotFoundException;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(String username, String password) throws AuthenticationException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Uživatel '" + username + "' nebyl nalezen."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Neplatné heslo");
        }

        return jwtUtil.generateToken(username);
    }


    public User createUser(UserRequestDto user) {
        if(userRepository.findByEmail((user.getEmail())).isPresent()){
            throw new DuplicateKeyException("Email already in use");
        }
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new DuplicateKeyException("Username already in use");
        }
        User registeredUser = user.toUser();
        registeredUser.setPassword(passwordEncoder.encode(registeredUser.getPassword()));
        return userRepository.save(registeredUser);
    }

    public UUID requestPasswordReset(UserRequestResetDto reqUser) {
        UUID token = UUID.randomUUID();
        Optional<User> user = userRepository.findByUsername(reqUser.getUsername());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Uživatel s uživatelským jménem " + reqUser.getUsername() + " nebyl nalezen");
        }
        user.ifPresent(u -> {
            u.setResetToken(token);
            u.setResetTokenExpiry(LocalDateTime.now().plusMinutes(15)); // token platný 15 minut
            userRepository.save(u);
            log.info("Password reset token for user {}: {}", u.getUsername(), token);
        });
        return token;
    }

    public void resetPassword(UserRequestPasswordDto userRequest) {
        Optional<User> user = userRepository.findByResetToken(userRequest.getToken());
        user.ifPresent(u -> {
            if (u.getResetTokenExpiry() == null || u.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Token je neplatný nebo vypršel");
            }
            u.setPassword(passwordEncoder.encode(userRequest.getNewPassword()));
            //delete token after use
            userRepository.save(u);
            log.info("Password reset token for user {}", u.getUsername());
        });
    }


    public void changePassword(UserChangePasswordDto reqUser) throws AuthenticationException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(passwordEncoder.matches(reqUser.getNewPassword(), user.getPassword())){
            throw new IllegalArgumentException("New password is same as old password");
        }

        login(user.getUsername(), reqUser.getOldPassword());
        user.setPassword(passwordEncoder.encode(reqUser.getNewPassword()));
        login(user.getUsername(), reqUser.getOldPassword());
        user.setPassword(passwordEncoder.encode(reqUser.getNewPassword()));
        userRepository.save(user);
    }
}
