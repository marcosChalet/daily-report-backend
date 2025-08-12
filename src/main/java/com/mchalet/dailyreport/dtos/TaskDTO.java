package com.mchalet.dailyreport.dtos;

import jakarta.validation.constraints.NotBlank;

public record TaskDTO(@NotBlank String task) { }
