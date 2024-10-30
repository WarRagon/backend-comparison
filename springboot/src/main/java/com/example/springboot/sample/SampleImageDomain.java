package com.example.springboot.sample;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SampleImageDomain {
    private String fileName;
    private int size;
    public void setSize(int size) {
        this.size = size;
    }
}
