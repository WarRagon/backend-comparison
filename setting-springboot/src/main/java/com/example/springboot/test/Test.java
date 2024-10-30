package com.example.springboot.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_test")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    
    private String ttt;
}