package com.mchalet.dailyreport.application.report.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ReportResponse(
        UUID id,
        String title,
        int type,
        List<TagResponse> tags,
        List<ReportTaskResponse> tasks,
        Instant createdAt,
        Instant updatedAt
) {}