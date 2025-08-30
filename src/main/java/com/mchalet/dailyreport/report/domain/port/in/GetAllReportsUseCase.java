package com.mchalet.dailyreport.report.domain.port.in;

import com.mchalet.dailyreport.report.application.dto.ReportFilter;
import com.mchalet.dailyreport.report.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllReportsUseCase {
    Page<Report> execute(ReportFilter filter, Pageable pageable);
}
