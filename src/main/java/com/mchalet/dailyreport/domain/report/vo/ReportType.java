package com.mchalet.dailyreport.domain.report.vo;

import com.mchalet.dailyreport.domain.shared.constraints.ValidationConstants;

public record ReportType(int value) {
    public ReportType {
        if (value > ValidationConstants.TYPE_MAX_VALUE || value < ValidationConstants.TYPE_MIN_VALUE) {
            throw new IllegalArgumentException("The type must be between " + ValidationConstants.TYPE_MIN_VALUE + " and " + ValidationConstants.TYPE_MAX_VALUE +" .");
        }
    }
}
