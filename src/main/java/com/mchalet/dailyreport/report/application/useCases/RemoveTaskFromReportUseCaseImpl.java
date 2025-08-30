package com.mchalet.dailyreport.report.application.useCases;

import com.mchalet.dailyreport.report.domain.port.in.RemoveTaskFromReportUseCase;
import com.mchalet.dailyreport.report.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.domain.shared.exception.ResourceNotFoundException;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveTaskFromReportUseCaseImpl implements RemoveTaskFromReportUseCase {
    private final ReportRepository reportRepository;

    public RemoveTaskFromReportUseCaseImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    @Transactional
    public void execute(ID reportId, ID taskId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        report.removeTask(taskId);
        reportRepository.save(report);
    }
}
