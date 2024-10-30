package com.example.springboot.sample;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.util.PageRequestDTO;
import com.example.springboot.util.PageResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SampleServiceImpl implements SampleService{
    private final SampleRepository sampleRepository;

    @Override
    public PageResponseDTO<SampleDTO> getList(PageRequestDTO pageRequestDTO) {
        log.info("getList....");

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() -1, pageRequestDTO.getSize(), Sort.by("no").descending());

        Page<Object[]> result = sampleRepository.selectList(pageable);

        List<SampleDTO> dtoList = result.get().map(arr -> {
            SampleDomain sample = (SampleDomain) arr[0];
            SampleImageDomain sampleImage = (SampleImageDomain) arr[1];

            SampleDTO sampleDTO = SampleDTO.builder()
            .no(sample.getNo())
            .name(sample.getName())
            .build();

            String imageStr = sampleImage.getFileName();
            sampleDTO.setUploadFileNames((List.of(imageStr)));

            return sampleDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<SampleDTO>withAll()
        .dtoList(dtoList)
        .totalCount(totalCount)
        .pageRequestDTO(pageRequestDTO)
        .build();
    }

    @Override
    public Long register(SampleDTO sampleDTO) {
        SampleDomain sample = dtoToEntity(sampleDTO);

        SampleDomain result = sampleRepository.save(sample);

        return result.getNo();
    }

    private SampleDomain dtoToEntity(SampleDTO sampleDTO) {
        SampleDomain sample = SampleDomain.builder()
        .no(sampleDTO.getNo())
        .name(sampleDTO.getName())
        .build();

        List<String> uploadFileNames = sampleDTO.getUploadFileNames();

        if(uploadFileNames == null) {
            return sample;
        }

        uploadFileNames.stream().forEach(uploadName -> {
            sample.addImageString(uploadName);
        });

        return sample;
    }

    @Override
    public SampleDTO get(Long no) {
        Optional<SampleDomain> result = sampleRepository.selectOne(no);

        SampleDomain sample = result.orElseThrow();

        SampleDTO sampleDTO = entityToDTO(sample);

        return sampleDTO;
    }

    private SampleDTO entityToDTO(SampleDomain sample) {
        SampleDTO sampleDTO = SampleDTO.builder()
        .no(sample.getNo())
        .name(sample.getName())
        .build();

        List<SampleImageDomain> imageList = sample.getImageList();

        if (imageList == null || imageList.size() == 0) {
            return sampleDTO;
        }

        List<String> fileNameList = imageList.stream().map(sampleImage -> sampleImage.getFileName()).toList();

        sampleDTO.setUploadFileNames(fileNameList);

        return sampleDTO;
    }

    @Override
    public void modify(SampleDTO sampleDTO) {
        Optional<SampleDomain> result = sampleRepository.findById(sampleDTO.getNo());

        SampleDomain sample = result.orElseThrow();

        sample.changeName("update!!");

        sample.clearList();

        List<String> uploadFileNames = sampleDTO.getUploadFileNames();

        if(uploadFileNames != null && uploadFileNames.size() > 0) {
            uploadFileNames.stream().forEach(uploadName -> {
                sample.addImageString(uploadName);
            });
        }
        sampleRepository.save(sample);
    }

    @Override
    public void remove(Long no) {
        sampleRepository.updateToDelete(no, "D");
    }
}
