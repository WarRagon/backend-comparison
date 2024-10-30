package com.example.springboot.sample;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "sample")
@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    private String name;

    @ElementCollection
    @Builder.Default
    private List<SampleImageDomain> imageList = new ArrayList<>();

    public void changeName(String name) {
        this.name = name;
    }

    public void addImage(SampleImageDomain image) {
        image.setSize(this.imageList.size());
        imageList.add(image);
    }

    public void addImageString(String fileName) {
        SampleImageDomain sampleImage = SampleImageDomain.builder()
        .fileName((fileName))
        .build();
        addImage(sampleImage);
    }

    public void clearList() {
        this.imageList.clear();
    }

    @Builder.Default
    private String status = "A";

    public void changeStatus(String statusFlag) {
        this.status = statusFlag;
    }
}
