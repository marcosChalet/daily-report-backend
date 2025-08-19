package com.mchalet.dailyreport.report.application.useCases;

import com.mchalet.dailyreport.report.domain.port.in.RemoveReportByIdUseCase;
import com.mchalet.dailyreport.report.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.report.domain.shared.exception.ResourceNotFoundException;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveReportByIdUseCaseImpl implements RemoveReportByIdUseCase {
    private final ReportRepository reportRepository;

    public RemoveReportByIdUseCaseImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    @Transactional
    public void execute(ID reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new ResourceNotFoundException("Report not found with id: " + reportId.value() + ". Cannot delete.");
        }
        reportRepository.deleteById(reportId);

    }
}
