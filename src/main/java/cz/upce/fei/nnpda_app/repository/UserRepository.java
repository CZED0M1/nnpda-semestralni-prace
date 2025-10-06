package cz.upce.fei.nnpda_app.repository;

import cz.upce.fei.nnpda_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByResetToken(UUID resetToken);
    Optional<User> findByEmail(String email);
}
