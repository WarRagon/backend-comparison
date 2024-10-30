package com.example.springboot.demo;

import com.example.springboot.util.PageRequestDTO;
import com.example.springboot.util.PageResponseDTO;

public interface DemoService {
    Long register(DemoDTO demoDTO);

    DemoDTO get(Long no);

    void modify(DemoDTO demoDTO);

    void remove(Long no);

    PageResponseDTO<DemoDTO> list(PageRequestDTO pageRequestDTO);
}
