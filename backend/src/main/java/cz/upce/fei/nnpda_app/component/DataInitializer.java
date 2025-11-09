package cz.upce.fei.nnpda_app.component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("DataInitializer executed");

    }
}
