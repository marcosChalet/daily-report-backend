package com.mchalet.dailyreport.report.domain.port.in;

import com.mchalet.dailyreport.report.domain.shared.vo.ID;

public interface RemoveTaskFromReportUseCase {
    void execute(ID reportId, ID taskId);
}
