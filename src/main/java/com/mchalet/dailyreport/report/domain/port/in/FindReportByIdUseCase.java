package com.mchalet.dailyreport.report.domain.port.in;

import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;

public interface FindReportByIdUseCase {
    Report execute(ID reportId);
}
