package com.mchalet.dailyreport.application.report.dto;

import com.mchalet.dailyreport.domain.shared.constraints.ValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record CreateReportRequest(
        @NotBlank(message = "Title cannot be blank")
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
