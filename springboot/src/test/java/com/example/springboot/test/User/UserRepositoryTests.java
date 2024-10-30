package com.example.springboot.test.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.springboot.user.UserDomain;
import com.example.springboot.user.UserRepository;
import com.example.springboot.user.UserRole;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class UserRepositoryTests {
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsetUser() {
        for (int i = 0; i < 10; i ++) {
            UserDomain user = UserDomain.builder()
            .email(i + "@mail.com")
            .password(passwordEncoder.encode("1"))
            .nickname("nickname" + i)
            .build();

            user.addRole(UserRole.USER);

            if (i >= 5) {
                user.addRole(UserRole.MANAGER);
            } 

            if (i >= 8 ) {
                user.addRole(UserRole.ADMIN);
            }

            userRepository.save(user);
        }
    }

    @Test
    public void testRead() {
        String email = "9@mail.com";
        UserDomain user = userRepository.getWithRoles(email);

        log.info("------");
        log.info(user);
        log.info(user.getUserRoleList());
    }
    
}
