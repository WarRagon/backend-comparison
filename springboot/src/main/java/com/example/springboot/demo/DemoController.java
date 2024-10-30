package com.example.springboot.demo;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.util.PageRequestDTO;
import com.example.springboot.util.PageResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/demo")
public class DemoController {
    private final DemoService service;

    @GetMapping("/{no}")
    public DemoDTO get(@PathVariable(name = "no") Long no) {
        return service.get(no);
    }

    @GetMapping("/list")
    public PageResponseDTO<DemoDTO> list(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);
        return service.list(pageRequestDTO);
    }

    @PostMapping("/")
    public Map<String, Long> registry(@RequestBody DemoDTO demoDTO) {
        log.info("DemoDTO: " + demoDTO);
        Long no = service.register(demoDTO);
        return Map.of("NO", no);
    }

    @PutMapping("/{no}")
    public Map<String, String> modify(
        @PathVariable(name="no") Long no,
        @RequestBody DemoDTO demoDTO) {
            demoDTO.setNo(no);
            log.info("Modify: " + demoDTO);
            service.modify(demoDTO);
            return Map.of("RESULT", "SUCCESS");
        }

    @DeleteMapping("/{no}")
    public Map<String, String> remove(@PathVariable(name="no") Long no) {
        log.info("Remove: " + no);
        service.remove(no);
        return Map.of("RESULT", "SUCCESS");

    }
}