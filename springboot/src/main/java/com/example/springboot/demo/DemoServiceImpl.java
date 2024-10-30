package com.example.springboot.demo;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.util.PageRequestDTO;
import com.example.springboot.util.PageResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class DemoServiceImpl implements DemoService{

    private final ModelMapper modelMapper;

    private final DemoRepository demoRepository;

    @Override
    public Long register(DemoDTO demoDTO) {
        log.info("...");

        DemoDomain demo = modelMapper.map(demoDTO, DemoDomain.class);

        DemoDomain savedDemo = demoRepository.save(demo);

        return savedDemo.getNo();
    }

    @Override
    public DemoDTO get(Long no) {
        Optional<DemoDomain> result =demoRepository.findById(no);
        
        DemoDomain demo = result.orElseThrow(() -> new NoSuchElementException("Demo not found with no: " + no));

        DemoDTO dto = modelMapper.map(demo, DemoDTO.class);

        return dto;
    }

    @Override
    public void modify(DemoDTO demoDTO) {

        Long no = demoDTO.getNo();

        Optional<DemoDomain> result = demoRepository.findById(no);
        
        DemoDomain demo = result.orElseThrow(() -> new NoSuchElementException("Demo not found with no: " + no));

        demo.changettt(demoDTO.getTtt());
        demo.changeddd(demoDTO.getDdd());

        demoRepository.save(demo);
    }

    @Override
    public void remove(Long no) {
        demoRepository.deleteById(no);
    }
    
    @Override
    public PageResponseDTO<DemoDTO> list(PageRequestDTO pageRequestDTO) {
        Pageable pageable = 
            PageRequest.of(
                pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("no").descending()
            );

        Page<DemoDomain> result = demoRepository.findAll(pageable);

        List<DemoDTO> dtoList = result.getContent().stream()
        .map(demo -> modelMapper.map(demo, DemoDTO.class))
        .collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        PageResponseDTO<DemoDTO> responseDTO = PageResponseDTO.<DemoDTO>withAll()
        .dtoList(dtoList)
        .pageRequestDTO(pageRequestDTO)
        .totalCount(totalCount)
        .build();

        return responseDTO;
    }
}
