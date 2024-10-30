package com.example.springboot.test.Demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

import com.example.springboot.demo.DemoDomain;
import com.example.springboot.demo.DemoRepository;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class DemoRepositoryUpdate {
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    private DemoRepository demoRepository;

    @Test
    public void testUpdate() {
        Long no = 1L;

        try {
            Optional<DemoDomain> result = demoRepository.findById(no);
            DemoDomain demo = result.orElseThrow(() -> new NoSuchElementException("Demo not found with no: " + no));       
            log.info(demo);
            demo.changettt("update!");
            demo.changeddd(LocalDate.of(2024,12,31));
            demoRepository.save(demo);
            demo = result.orElseThrow(() -> new NoSuchElementException("Demo not found with no: " + no));            
            log.info(demo);
        } catch (Exception e) {
            log.error("Error during update operation", e);
        }
    }
}
