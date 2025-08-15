package com.mchalet.dailyreport.infrastructure.persistence.jpa.repository;

import com.mchalet.dailyreport.infrastructure.persistence.jpa.entity.ReportJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportSpringDataRepository extends JpaRepository<ReportJpaEntity, UUID>,
                                                        JpaSpecificationExecutor<ReportJpaEntity> {}
