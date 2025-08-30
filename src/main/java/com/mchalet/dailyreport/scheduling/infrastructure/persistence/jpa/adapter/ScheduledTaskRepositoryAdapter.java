package com.mchalet.dailyreport.scheduling.infrastructure.persistence.jpa.adapter;

import com.mchalet.dailyreport.scheduling.infrastructure.mapper.ScheduledTaskMapper;
import com.mchalet.dailyreport.scheduling.infrastructure.persistence.jpa.entity.ScheduledTaskJpaEntity;
import com.mchalet.dailyreport.scheduling.infrastructure.persistence.jpa.repository.ScheduledTaskSpringDataRepository;
import com.mchalet.dailyreport.scheduling.domain.ScheduledTask;
import com.mchalet.dailyreport.scheduling.domain.port.out.ScheduledTaskRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduledTaskRepositoryAdapter implements ScheduledTaskRepository {
    private final ScheduledTaskSpringDataRepository jpaRepository;
    private final ScheduledTaskMapper mapper;

    public ScheduledTaskRepositoryAdapter(
            ScheduledTaskSpringDataRepository jpaRepository,
            ScheduledTaskMapper mapper
    ) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ScheduledTask> findById(Long taskId) {
        return jpaRepository.findById(taskId).map(mapper::toDomain);
    }

    @Override
    public ScheduledTask save(ScheduledTask task) {
        if (task.getId() == null) {
            ScheduledTaskJpaEntity newEntity = mapper.toEntity(task);
            ScheduledTaskJpaEntity savedEntity = jpaRepository.save(newEntity);
            return mapper.toDomain(savedEntity);
        }

        ScheduledTaskJpaEntity entityToUpdate = jpaRepository.findById(task.getId())
                .orElseThrow(() -> new RuntimeException("Task not found for update: " + task.getId()));

        mapper.updateEntityFromDomain(task, entityToUpdate);

        ScheduledTaskJpaEntity savedEntity = jpaRepository.save(entityToUpdate);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<ScheduledTask> findAll() {
        List<ScheduledTaskJpaEntity> allEntities = jpaRepository.findAll();

        List<ScheduledTask> allScheduledTasks = new ArrayList<>();
        for (var entity : allEntities) {
            allScheduledTasks.add(mapper.toDomain(entity));
        }

        return allScheduledTasks;
    }

    @Override
    public List<ScheduledTask> findAllActive() {
        List<ScheduledTaskJpaEntity> activeEntities = jpaRepository.findAllByNextExecutionTimeIsNotNull();

        List<ScheduledTask> allScheduledTasks = new ArrayList<>();
        for (var entity : activeEntities) {
            allScheduledTasks.add(mapper.toDomain(entity));
        }

        return allScheduledTasks;
    }
}
