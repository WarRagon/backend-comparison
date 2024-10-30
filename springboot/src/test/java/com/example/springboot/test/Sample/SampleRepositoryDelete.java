package com.example.springboot.test.Sample;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.sample.SampleDomain;
import com.example.springboot.sample.SampleRepository;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.log4j.Log4j2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
@Log4j2
public class SampleRepositoryDelete {    
    @BeforeAll
    static void setUp() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    }

    @Autowired
    SampleRepository sampleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Commit
    @Transactional
    @Test
    public void testDelete() {
        Long no = sampleRepository.findMaxNo();
        Optional<SampleDomain> result = sampleRepository.selectOne(no);
        log.info(result);

        sampleRepository.updateToDelete(no, "D");

        entityManager.flush();
        entityManager.clear(); 

        result = sampleRepository.selectOne(no);

        log.info(result);
    }
}
