package com.example.springboot.test.Sample;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springboot.sample.SampleDTO;
import com.example.springboot.sample.SampleDomain;
import com.example.springboot.sample.SampleRepository;
import com.example.springboot.sample.SampleService;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SampleServiceInsert {
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
    public void testRegister() {
        SampleDTO sampleDTO = SampleDTO.builder()
        .name("test")
        .build();

        sampleDTO.setUploadFileNames(
            List.of(
                UUID.randomUUID() + "_" + "1.jpg",
                UUID.randomUUID() + "_" + "2.jpg"));
        Long no = sampleService.register(sampleDTO);
        
        log.info("no:" + no);
        
        Optional<SampleDomain> result2 = sampleRepository.selectOne(no);

        SampleDomain sample2 = result2.orElseThrow();

        log.info(sample2);
        log.info(sample2.getImageList());

    }
    
}
