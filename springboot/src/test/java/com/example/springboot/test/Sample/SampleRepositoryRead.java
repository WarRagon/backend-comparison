package com.example.springboot.test.Sample;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.sample.SampleDomain;
import com.example.springboot.sample.SampleRepository;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SampleRepositoryRead {    
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    SampleRepository sampleRepository;

    @Transactional
    @Test
    public void testRead() {
        Long no = sampleRepository.findMaxNo();

        Optional<SampleDomain> result = sampleRepository.findById(no);

        SampleDomain sample = result.orElseThrow();

        log.info(sample);
        log.info(sample.getImageList());
    }

    @Test
    public void testRead2() {

        Long no = sampleRepository.findMaxNo();

        Optional<SampleDomain> result = sampleRepository.selectOne(no);

        SampleDomain sample = result.orElseThrow();

        log.info(sample);
        log.info(sample.getImageList());
    }
}
