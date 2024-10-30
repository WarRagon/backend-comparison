package com.example.springboot.test.Sample;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springboot.sample.SampleDomain;
import com.example.springboot.sample.SampleRepository;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SampleRepositoryInsert {    
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    SampleRepository sampleRepository;

    @BeforeEach
    public void clearDatabase() {
        sampleRepository.deleteAll();    
    }

    @Test
    public void testinsert() {
        List<SampleDomain> samples = new ArrayList<>();

        try {

            for (int i = 0; i < 10; i++) {
                SampleDomain sample = SampleDomain.builder()
                .name("name" + i)
                .build();
    
                sample.addImageString(UUID.randomUUID().toString()+ "_" + "1.jpg");
                sample.addImageString(UUID.randomUUID().toString()+ "_" + "2.jpg");
    
                //sampleRepository.save(sample);
                
                samples.add(sample);    
            }
            
            sampleRepository.saveAll(samples);
            log.info("------");

        } catch (Exception e) {
            log.error("Error during insert operation", e);
        }
    }
}
