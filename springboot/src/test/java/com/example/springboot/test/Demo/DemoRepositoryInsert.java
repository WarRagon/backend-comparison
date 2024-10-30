package com.example.springboot.test.Demo;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

import com.example.springboot.demo.DemoDomain;
import com.example.springboot.demo.DemoRepository;

import java.util.List;

@SpringBootTest
@Log4j2
public class DemoRepositoryInsert {
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    private DemoRepository demoRepository;

    @BeforeEach
    public void clearDatabase() {
        demoRepository.deleteAll();    }

    @Test
    public void testInsert() {
        List<DemoDomain> demos = new ArrayList<>();

        try {
                for (int i = 1; i <= 100; i ++) {
                    DemoDomain demo = DemoDomain.builder().no((long) i).ttt("ttt" + i).ddd(LocalDate.of(2024,10,27)).build();

                    //demoRepository.save(demo);
                    demos.add(demo);
                }
                demoRepository.saveAll(demos);
            } catch (Exception e) {
            log.error("Error during insert operation", e);
        }
    }
}