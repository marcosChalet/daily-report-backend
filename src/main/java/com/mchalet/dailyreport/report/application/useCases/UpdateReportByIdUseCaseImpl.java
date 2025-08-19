package com.mchalet.dailyreport.report.application.useCases;

import com.mchalet.dailyreport.report.application.dto.UpdateReportRequest;
import com.mchalet.dailyreport.report.domain.port.in.UpdateReportByIdUseCase;
import com.mchalet.dailyreport.report.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.domain.vo.ReportType;
import com.mchalet.dailyreport.report.domain.vo.Title;
import com.mchalet.dailyreport.report.domain.shared.exception.ResourceNotFoundException;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import com.mchalet.dailyreport.report.infrastructure.mapper.TagMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateReportByIdUseCaseImpl implements UpdateReportByIdUseCase {
    private final ReportRepository reportRepository;
    private final TagMapper tagMapper;

    public UpdateReportByIdUseCaseImpl(ReportRepository reportRepository, TagMapper tagMapper) {
        this.reportRepository = reportRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional
    public Report execute(ID reportId, UpdateReportRequest request) {
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
}
