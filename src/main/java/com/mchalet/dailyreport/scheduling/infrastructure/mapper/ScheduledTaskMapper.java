package com.mchalet.dailyreport.scheduling.infrastructure.mapper;

import com.mchalet.dailyreport.scheduling.domain.ScheduledTask;
import com.mchalet.dailyreport.scheduling.infrastructure.persistence.jpa.entity.ScheduledTaskJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ScheduledTaskMapper {
    /**
     * Converts a JPA entity into a domain ScheduledTask.
     */
    ScheduledTask toDomain(ScheduledTaskJpaEntity entity);

    /**
     * Converts a domain ScheduledTask into a JPA entity for persistence.
     */
    ScheduledTaskJpaEntity toEntity(ScheduledTask scheduledTask);

    /**
     * Converts a domain ScheduledTask into a JPA entity for updates in persistence.
     */
    void updateEntityFromDomain(ScheduledTask scheduledTask, @MappingTarget ScheduledTaskJpaEntity entity);
}
