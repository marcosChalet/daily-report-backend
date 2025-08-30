package com.mchalet.dailyreport.report.domain.port.in;

import com.mchalet.dailyreport.report.application.dto.CreateReportRequest;
import com.mchalet.dailyreport.report.domain.Report;

public interface CreateReportUseCase {
    Report execute(CreateReportRequest request);
}
