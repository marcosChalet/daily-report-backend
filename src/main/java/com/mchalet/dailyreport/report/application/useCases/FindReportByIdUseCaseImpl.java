package com.mchalet.dailyreport.report.application.useCases;

import com.mchalet.dailyreport.report.domain.port.in.FindReportByIdUseCase;
import com.mchalet.dailyreport.report.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.domain.shared.exception.ResourceNotFoundException;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindReportByIdUseCaseImpl implements FindReportByIdUseCase {
    private final ReportRepository reportRepository;

    public FindReportByIdUseCaseImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Report execute(ID reportId) {
        return reportRepository.findById(reportId).orElseThrow(
                () -> new ResourceNotFoundException("Report with id '" + reportId.value().toString() + "'not found.")
        );
    }
}
