package com.mchalet.dailyreport.report.domain.port.in;

import com.mchalet.dailyreport.report.application.dto.UpdateReportRequest;
import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;

public interface UpdateReportByIdUseCase {
    Report execute(ID reportId, UpdateReportRequest request);
}
