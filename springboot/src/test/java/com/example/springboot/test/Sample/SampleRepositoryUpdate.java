package com.example.springboot.test.Sample;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springboot.sample.SampleDomain;
import com.example.springboot.sample.SampleRepository;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SampleRepositoryUpdate {    
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    SampleRepository sampleRepository;

    @Test
    public void testUpdate() {
        Long no = sampleRepository.findMaxNo();
        SampleDomain result = sampleRepository.selectOne(no).get();
        log.info(result);

        Optional<SampleDomain> result2 = sampleRepository.selectOne(no);

        SampleDomain sample = result2.orElseThrow();
        log.info(sample);
        log.info(sample.getImageList());

        result.changeName("update");

        result.clearList();

        result.addImageString(UUID.randomUUID().toString() + "_" + "1.jpg");
        result.addImageString(UUID.randomUUID().toString() + "_" + "2.jpg");
        result.addImageString(UUID.randomUUID().toString() + "_" + "3.jpg");

        sampleRepository.save(result);

        result = sampleRepository.selectOne(no).get();

        log.info(result);

        result2 = sampleRepository.selectOne(no);

        sample = result2.orElseThrow();

        log.info(sample);
        log.info(sample.getImageList());
    }
}
