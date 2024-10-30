package com.example.springboot.test.Demo;

import org.springframework.data.domain.Pageable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.springboot.demo.DemoDomain;
import com.example.springboot.demo.DemoRepository;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class DemoRepositoryPaging {
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
    public void testPaging() {
        Pageable pageable = PageRequest.of(0,3, Sort.by("no").descending());
        Page<DemoDomain> result = demoRepository.findAll(pageable);
    
        log.info(result.getTotalElements());

        result.getContent().stream().forEach(demo -> log.info(demo));
    }
}
