package com.mchalet.dailyreport.dtos;

import com.mchalet.dailyreport.model.TagModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TaskListDTO(@NotBlank String title,
                          @NotNull Integer taskType,
                          List<TagModel> tags) { }
