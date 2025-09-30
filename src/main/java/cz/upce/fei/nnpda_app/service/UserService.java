package cz.upce.fei.nnpda_app.service;

import cz.upce.fei.nnpda_app.dto.UserRequestDto;
import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return false; // uživatel nenalezen
        }

        User user = userOpt.get();

        // ověření hesla přes PasswordEncoder (BCrypt apod.)
        return passwordEncoder.matches(password, user.getPassword());
    }

    public User createUser(UserRequestDto user) {
        User registeredUser = user.toUser();
        registeredUser.setPassword(passwordEncoder.encode(registeredUser.getPassword()));
        return userRepository.save(registeredUser);
    }
}
