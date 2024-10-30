package com.example.springboot.demo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "demo")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class DemoDomain {
    @Id
    private Long no;
    
    private String ttt;

    public void changettt(String ttt){
        this.ttt = ttt;
    };

    private LocalDate ddd;

    public void changeddd(LocalDate ddd){
        this.ddd = ddd;
    }
}