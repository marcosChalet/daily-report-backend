package com.mchalet.dailyreport.scheduling.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class GenericTaskCreateRequest {
    private String name;
    private String actionBeanName;
    private String frequency;
    private LocalTime timeOfDay;
    private LocalDate expirationDate;
}
