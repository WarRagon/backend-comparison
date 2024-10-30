package com.example.springboot.test.Demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.demo.DemoDTO;
import com.example.springboot.demo.DemoService;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class DemoServiceUpdate {
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    private DemoService demoService;

    @Test
    @Transactional
    public void testModify() {
        Long no = 1L;

        DemoDTO demoDTO = demoService.get(no);

        log.info(demoDTO);

        DemoDTO updateDTO = DemoDTO.builder()
        .no(demoDTO.getNo())
        .ttt("Service Update!!")
        .ddd(demoDTO.getDdd())
        .build();

        demoService.modify(updateDTO);

        demoDTO = demoService.get(no);

        log.info(demoDTO);        

        demoDTO.setTtt("Update Service!!");

        demoService.modify(demoDTO);

        demoDTO = demoService.get(no);

        log.info(demoDTO);
    }
    
}