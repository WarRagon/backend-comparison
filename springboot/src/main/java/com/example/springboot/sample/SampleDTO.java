package com.example.springboot.sample;

import java.util.List;
import java.util.ArrayList;


import org.springframework.web.multipart.MultipartFile;

//import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleDTO {
    private Long no;

    private String name;

    //@ArraySchema(schema = @Schema(type = "string", format = "binary"))
    @Schema(type = "array", format = "binary")
    @Builder.Default    
    private List<MultipartFile> files = new ArrayList<>();

    @Builder.Default
    private List<String> uploadFileNames = new ArrayList<>();
}
