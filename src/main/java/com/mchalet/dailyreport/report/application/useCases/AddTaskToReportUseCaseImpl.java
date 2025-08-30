package com.mchalet.dailyreport.report.application.useCases;

import com.mchalet.dailyreport.report.application.dto.ReportTaskRequest;
import com.mchalet.dailyreport.report.domain.port.in.AddTaskToReportUseCase;
import com.mchalet.dailyreport.report.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.domain.ReportTask;
import com.mchalet.dailyreport.report.domain.shared.exception.ResourceNotFoundException;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import com.mchalet.dailyreport.report.infrastructure.mapper.ReportTaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddTaskToReportUseCaseImpl implements AddTaskToReportUseCase {
    private final ReportRepository reportRepository;
    private final ReportTaskMapper mapper;

    public AddTaskToReportUseCaseImpl(ReportRepository reportRepository, ReportTaskMapper mapper) {
        this.reportRepository = reportRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Report execute(ID reportId, ReportTaskRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        ReportTask newTask  = mapper.toDomain(request);
        report.addTask(newTask);
        reportRepository.save(report);
        return report;
    }
}
