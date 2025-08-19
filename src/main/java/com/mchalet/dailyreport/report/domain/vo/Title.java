package com.mchalet.dailyreport.report.domain.vo;

import com.mchalet.dailyreport.report.domain.shared.constraints.ValidationConstants;

public record Title(String value) {
    public Title {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank.");
        }
        if (value.length() < ValidationConstants.TITLE_MIN_LENGTH) {
            throw new IllegalArgumentException("Title must have at least " + ValidationConstants.TITLE_MIN_LENGTH + " characters.");
        }
        if (value.length() > ValidationConstants.TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException("Title cannot exceed " + ValidationConstants.TITLE_MAX_LENGTH + " characters.");
        }
    }
}
