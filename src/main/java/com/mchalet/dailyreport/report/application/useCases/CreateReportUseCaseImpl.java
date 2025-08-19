package com.mchalet.dailyreport.report.application.useCases;

import com.mchalet.dailyreport.report.application.dto.CreateReportRequest;
import com.mchalet.dailyreport.report.domain.port.in.CreateReportUseCase;
import com.mchalet.dailyreport.report.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.infrastructure.mapper.ReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateReportUseCaseImpl implements CreateReportUseCase {
    private final ReportRepository reportRepository;
    private final ReportMapper mapper;

    public CreateReportUseCaseImpl(ReportRepository reportRepository, ReportMapper mapper) {
        this.reportRepository = reportRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Report execute(CreateReportRequest request) {
        Report reportToCreate = mapper.toDomain(request);
        return reportRepository.save(reportToCreate);
    }
}
