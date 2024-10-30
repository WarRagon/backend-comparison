package com.example.springboot.test.Demo;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.demo.DemoDTO;
import com.example.springboot.demo.DemoDomain;
import com.example.springboot.demo.DemoRepository;
import com.example.springboot.demo.DemoService;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class DemoServiceInsert {
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    private DemoService demoService;
    
    @Autowired
    private DemoRepository demoRepository;

    @Test
    @Transactional
    public void testRegister() {
        DemoDTO demoDTO = DemoDTO.builder()
        .no((long) 100)
        .ttt("test")
        .ddd(LocalDate.of(2024,01,01))
        .build();

        Long no = demoService.register(demoDTO);
        log.info("no:" + no);

        Optional<DemoDomain> result = demoRepository.findById(no);
        DemoDomain demo = result.orElseThrow(() -> new NoSuchElementException("Demo not found with no: " + no));
        log.info(demo);
    }
    
}