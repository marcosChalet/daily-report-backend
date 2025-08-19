package com.mchalet.dailyreport.report.application.dto;

import java.time.Instant;
import java.util.List;

public record ReportFilter(
        String title,
        Integer type,
        Instant dateFrom,
        Instant dateTo,
        List<String> tags
) {}
