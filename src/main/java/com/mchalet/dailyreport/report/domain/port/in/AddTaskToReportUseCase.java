package com.mchalet.dailyreport.report.domain.port.in;

import com.mchalet.dailyreport.report.application.dto.ReportTaskRequest;
import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;

public interface AddTaskToReportUseCase {
    Report execute(ID reportId, ReportTaskRequest request);
}
