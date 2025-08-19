package com.mchalet.dailyreport.report.application.dto;

import com.mchalet.dailyreport.report.domain.shared.constraints.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateReportRequest(
        @Size(
                min = ValidationConstants.TITLE_MIN_LENGTH,
                max = ValidationConstants.TITLE_MAX_LENGTH,
                message = "Title must be between {min} and {max} characters."
        )
        String title,

        @NotNull(message = "Type cannot be null")
        @Min(
                value = ValidationConstants.TYPE_MIN_VALUE,
                message = "The type must be between {min} and {max}."
        )
        @Max(
                value = ValidationConstants.TYPE_MAX_VALUE,
                message = "The type must be between {min} and {max}."
        )
        Integer type,

        @Valid
        List<TagRequest> tags
) {}
