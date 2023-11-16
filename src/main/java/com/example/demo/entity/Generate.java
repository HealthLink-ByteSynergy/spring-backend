package com.example.demo.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Generate {
    private String truncate;
    private String return_likelihoods;
    private String prompt;
    private List<Generations> generations;
}
