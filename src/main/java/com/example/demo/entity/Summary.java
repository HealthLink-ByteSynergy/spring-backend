package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Summary { 
    private String length;
    private String format;
    private String extractiveness;
    private double temperature;
    private String text;
    private String summary;
}
