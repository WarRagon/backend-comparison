package com.example.springboot.test.Sample;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springboot.sample.SampleDTO;
import com.example.springboot.sample.SampleService;
import com.example.springboot.util.PageRequestDTO;
import com.example.springboot.util.PageResponseDTO;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SampleServiceList {
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    SampleService sampleService;

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        PageResponseDTO<SampleDTO> result = sampleService.getList(pageRequestDTO);

        result.getDtoList().forEach(dto -> log.info(dto));
    }
    
}
