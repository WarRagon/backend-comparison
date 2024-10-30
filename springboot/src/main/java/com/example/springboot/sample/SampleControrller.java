package com.example.springboot.sample;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.util.CustomFileUtil;
import com.example.springboot.util.PageRequestDTO;
import com.example.springboot.util.PageResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/sample")
public class SampleControrller {
    private final CustomFileUtil fileUtil;
    private final SampleService sampleService;
    
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> register(
        @RequestParam("no") Long no,
        @RequestParam("name") String name,
        @RequestParam("files") List<MultipartFile> files
    ) {
        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.setNo(no);
        sampleDTO.setName(name);
        sampleDTO.setFiles(files);

        log.info("register: " + sampleDTO);

        List<String> uploadFileNames = fileUtil.saveFiles(files);
        sampleDTO.setUploadFileNames(uploadFileNames);
        log.info(uploadFileNames);

        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable("fileName") String fileName) {
        return fileUtil.getFile(fileName);
    }    

    @GetMapping("/delete/{fileNames}")
    public ResponseEntity<Map<String, String>> deleteFile(@PathVariable("fileNames") String fileNames) {
        List<String> fileNamesList = Arrays.asList(fileNames.split(","));
        fileUtil.deleteFiles(fileNamesList);
        return ResponseEntity.ok(Map.of("RESULT", "SUCCESS"));
    }

    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public PageResponseDTO<SampleDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("list -> " + pageRequestDTO);

        return sampleService.getList(pageRequestDTO);
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Long> register(
        @RequestParam("name") String name,
        @RequestParam("files") List<MultipartFile> files
        ) {
        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.setName(name);
        sampleDTO.setFiles(files);

        log.info("register: " + sampleDTO);

        List<String> uploadFileNames = fileUtil.saveFiles(files);

        sampleDTO.setUploadFileNames(uploadFileNames);

        log.info(uploadFileNames);

        Long no = sampleService.register(sampleDTO);

        return Map.of("result", no);
    }
    
    @GetMapping("/{no}")
    public SampleDTO read(@PathVariable("no") Long no) {
        return sampleService.get(no);
    }    

    @PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> modify(
        @RequestParam("no") Long no,
        @RequestParam("files") List<MultipartFile> files,
        @RequestParam(value = "uploadedfilenames", required = false) List<String> uploadedFileNames) {

        SampleDTO oldSampleDTO = sampleService.get(no);
        log.info(oldSampleDTO);

        List<String> oldFileNames = oldSampleDTO.getUploadFileNames();

        log.info(oldFileNames);

        List<String> finalUploadedFileNames = (uploadedFileNames != null) ? new ArrayList<>(uploadedFileNames) : new ArrayList<>();
        
        log.info(finalUploadedFileNames);

        if (oldFileNames != null && oldFileNames.size() > 0) {            
            List<String> removeFiles = oldFileNames.stream()
            .filter(fileName -> !finalUploadedFileNames.contains(fileName))
            .collect(Collectors.toList());
            
            fileUtil.deleteFiles(removeFiles);
        }

        List<String> currentUploadFileNames = fileUtil.saveFiles(files);

        if(currentUploadFileNames != null && currentUploadFileNames.size() > 0) {
            finalUploadedFileNames.addAll(currentUploadFileNames);
        }

        oldSampleDTO.setUploadFileNames(finalUploadedFileNames);
        sampleService.modify(oldSampleDTO);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{no}")
    public Map<String, String> remove(@PathVariable("no") Long no) {
        List<String> oldFileNames = sampleService.get(no).getUploadFileNames();
        sampleService.remove(no);
        fileUtil.deleteFiles(oldFileNames);
        return Map.of("RESULT", "SUCCESS");
    }
}
