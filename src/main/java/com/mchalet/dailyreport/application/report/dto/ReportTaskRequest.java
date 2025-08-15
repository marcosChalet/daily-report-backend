package com.mchalet.dailyreport.application.report.dto;

import com.mchalet.dailyreport.domain.shared.constraints.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReportTaskRequest(
        @NotBlank(
                message = "Task must have content."
        )
        @Size(
                max = ValidationConstants.TASK_MAX_LENGTH,
                message = "Task cannot exceed {max} characters."
        )
        String value
) {}