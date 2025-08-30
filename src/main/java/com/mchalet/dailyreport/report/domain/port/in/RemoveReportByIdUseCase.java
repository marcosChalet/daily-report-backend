package com.mchalet.dailyreport.report.domain.port.in;

import com.mchalet.dailyreport.report.domain.shared.vo.ID;

public interface RemoveReportByIdUseCase {
    void execute(ID reportId);
}
