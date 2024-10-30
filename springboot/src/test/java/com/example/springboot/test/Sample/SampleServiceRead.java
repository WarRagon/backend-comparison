package com.example.springboot.test.Sample;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springboot.sample.SampleDTO;
import com.example.springboot.sample.SampleRepository;
import com.example.springboot.sample.SampleService;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SampleServiceRead {
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    SampleService sampleService;

    @Autowired
    SampleRepository sampleRepository;

    @Test
    public void testRead() {        
        Long no = sampleRepository.findMaxNo();

        SampleDTO sampleDTO = sampleService.get(no);

        log.info(sampleDTO);
        log.info(sampleDTO.getUploadFileNames());
    }
    
}
