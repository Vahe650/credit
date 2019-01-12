package app.credit;

import app.credit.model.User;
import app.credit.model.UserType;
import app.credit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication


public class CreditApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) { SpringApplication.run(CreditApplication.class, args);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByEmail("ando") == null) {
            User admin = User.builder()
                    .name("Ando")
                    .type(UserType.ADMIN)
                    .email("ando")
                    .password(passwordEncoder().encode("ando"))
                    .build();

            userRepository.save(admin);
        }
    }


}
