package com.mchalet.dailyreport.application.report.service;

import com.mchalet.dailyreport.application.report.dto.ReportTaskRequest;
import com.mchalet.dailyreport.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.domain.report.Report;
import com.mchalet.dailyreport.domain.report.ReportTask;
import com.mchalet.dailyreport.domain.shared.exception.ResourceNotFoundException;
import com.mchalet.dailyreport.domain.shared.vo.ID;
import com.mchalet.dailyreport.infrastructure.mapper.ReportTaskMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReportTaskService {
    private final ReportRepository reportRepository;
    private final ReportTaskMapper mapper;

    public ReportTaskService(ReportRepository reportRepository, ReportTaskMapper mapper) {
        this.reportRepository = reportRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Report addTask(ID reportId, ReportTaskRequest request) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        ReportTask newTask  = mapper.toDomain(request);
        report.addTask(newTask);
        reportRepository.save(report);
        return report;
    }

    @Transactional
    public void removeTaskFromReport(ID reportId, ID taskId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found"));
        report.removeTask(taskId);
        reportRepository.save(report);
    }
}
