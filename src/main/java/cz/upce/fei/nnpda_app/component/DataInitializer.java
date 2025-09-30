package cz.upce.fei.nnpda_app.component;

import cz.upce.fei.nnpda_app.model.User;
import cz.upce.fei.nnpda_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        log.info("DataInitializer executed");
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setEmail("rob@rob.cz");
        if (userRepository.findByUsername("admin").isEmpty()) {
            log.info("Creating user");
            userRepository.save(user);
        }

        List<User> users = userRepository.findAll();

    }
}
