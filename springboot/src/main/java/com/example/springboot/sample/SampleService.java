package com.example.springboot.sample;

import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.util.PageRequestDTO;
import com.example.springboot.util.PageResponseDTO;

@Transactional
public interface SampleService {
    PageResponseDTO<SampleDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(SampleDTO sampleDTO);

    SampleDTO get(Long no);

    void modify(SampleDTO sampleDTO);

    void remove(Long no);
}
