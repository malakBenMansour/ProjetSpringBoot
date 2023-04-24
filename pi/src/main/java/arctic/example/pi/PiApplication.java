package arctic.example.pi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableScheduling
public class PiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PiApplication.class, args);
    }

}
