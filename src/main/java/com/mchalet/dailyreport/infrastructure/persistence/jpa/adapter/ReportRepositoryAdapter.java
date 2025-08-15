package com.mchalet.dailyreport.infrastructure.persistence.jpa.adapter;

import com.mchalet.dailyreport.application.report.dto.ReportFilter;
import com.mchalet.dailyreport.domain.port.out.ReportRepository;
import com.mchalet.dailyreport.domain.report.Report;
import com.mchalet.dailyreport.domain.shared.vo.ID;
import com.mchalet.dailyreport.infrastructure.persistence.jpa.ReportSpecification;
import com.mchalet.dailyreport.infrastructure.persistence.jpa.entity.ReportJpaEntity;
import com.mchalet.dailyreport.infrastructure.mapper.ReportMapper;
import com.mchalet.dailyreport.infrastructure.persistence.jpa.repository.ReportSpringDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ReportRepositoryAdapter implements ReportRepository {
    private final ReportSpringDataRepository jpaRepository;
    private final ReportMapper mapper;

    public ReportRepositoryAdapter(ReportSpringDataRepository jpaRepository, ReportMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Report save(Report report) {
        ReportJpaEntity savedEntity = jpaRepository.save(
                mapper.toEntity(report)
        );
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Page<Report> findAll(ReportFilter filter, Pageable pageable) {
        Specification<ReportJpaEntity> spec = ReportSpecification.build(filter);
        Page<ReportJpaEntity> pageOfEntities = jpaRepository.findAll(spec, pageable);
        return pageOfEntities.map(mapper::toDomain);
    }

    @Override
    public Optional<Report> findById(ID reportId) {
        return jpaRepository.findById(reportId.value())
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsById(ID reportId) {
        return jpaRepository.existsById(reportId.value());
    }

    @Override
    public void deleteById(ID reportId) {
        jpaRepository.deleteById(reportId.value());
    }
}
