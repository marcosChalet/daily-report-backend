package com.mchalet.dailyreport.report.application.useCases;

import com.mchalet.dailyreport.report.application.dto.ReportFilter;
import com.mchalet.dailyreport.report.domain.port.in.GetAllReportsUseCase;
import com.mchalet.dailyreport.report.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.report.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetAllReportsUseCaseImpl implements GetAllReportsUseCase {
    private final ReportRepository reportRepository;

    public GetAllReportsUseCaseImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Report> execute(ReportFilter filter, Pageable pageable) {
        return reportRepository.findAll(filter, pageable);
    }
}
