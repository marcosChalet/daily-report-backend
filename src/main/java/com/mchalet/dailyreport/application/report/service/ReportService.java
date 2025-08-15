package com.mchalet.dailyreport.application.report.service;

import com.mchalet.dailyreport.application.report.dto.CreateReportRequest;
import com.mchalet.dailyreport.application.report.dto.ReportFilter;
import com.mchalet.dailyreport.application.report.dto.UpdateReportRequest;
import com.mchalet.dailyreport.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.domain.report.Report;
import com.mchalet.dailyreport.domain.report.vo.ReportType;
import com.mchalet.dailyreport.domain.report.vo.Title;
import com.mchalet.dailyreport.domain.shared.exception.ResourceNotFoundException;
import com.mchalet.dailyreport.domain.shared.vo.ID;
import com.mchalet.dailyreport.infrastructure.mapper.ReportMapper;
import com.mchalet.dailyreport.infrastructure.mapper.TagMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper mapper;
    private final TagMapper tagMapper;

    public ReportService(ReportRepository reportRepository, ReportMapper mapper, TagMapper tagMapper) {
        this.reportRepository = reportRepository;
        this.mapper = mapper;
        this.tagMapper = tagMapper;
    }

    @Transactional
    public Report createReport(CreateReportRequest request) {
        Report reportToCreate = mapper.toDomain(request);
        return reportRepository.save(reportToCreate);
    }

    @Transactional(readOnly = true)
    public Page<Report> getAllReports(ReportFilter filter, Pageable pageable) {
        return reportRepository.findAll(filter, pageable);
    }

    @Transactional(readOnly = true)
    public Report getReportById(ID id) {
        return reportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Report with id '" + id.value().toString() + "'not found.")
        );
    }

    @Transactional
    public Report updateReport(ID reportId, UpdateReportRequest request) {
        Report existingReport = reportRepository
                .findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + reportId.value()));

        if (request.title() != null) {
            existingReport.changeTitle(new Title(request.title()));
        }

        if (request.type() != null) {
            existingReport.changeType(new ReportType(request.type()));
        }

        if (request.tags() != null) {
            existingReport.changeTags(
                    request
                            .tags()
                            .stream()
                            .map(tagMapper::toDomain)
                            .toList()
            );
        }

        return reportRepository.save(existingReport);
    }

    @Transactional
    public void deleteReportById(ID reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new ResourceNotFoundException("Report not found with id: " + reportId.value() + ". Cannot delete.");
        }
        reportRepository.deleteById(reportId);
    }
}
