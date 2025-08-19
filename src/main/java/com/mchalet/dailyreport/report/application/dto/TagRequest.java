package com.mchalet.dailyreport.report.application.dto;

import com.mchalet.dailyreport.report.domain.shared.constraints.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TagRequest(
        @NotBlank(message = "Tag cannot be blank.")
        @Size(max = ValidationConstants.TAG_MAX_LENGTH,
                message = "Tag cannot exceed {max} characters.")
        @Pattern(
                regexp = ValidationConstants.TAG_PATTERN,
                message = "Tag format is invalid. Use only letters, numbers, and single hyphens."
        )
        String value
) {}
