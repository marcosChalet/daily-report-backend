package com.mchalet.dailyreport.scheduling.infrastructure.persistence.jpa.repository;

import com.mchalet.dailyreport.scheduling.infrastructure.persistence.jpa.entity.ScheduledTaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ScheduledTaskSpringDataRepository  extends JpaRepository<ScheduledTaskJpaEntity, Long>,
        JpaSpecificationExecutor<ScheduledTaskJpaEntity> {

    List<ScheduledTaskJpaEntity> findAllByNextExecutionTimeIsNotNull();

}
