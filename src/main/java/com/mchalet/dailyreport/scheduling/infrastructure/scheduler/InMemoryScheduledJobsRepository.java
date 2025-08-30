package com.mchalet.dailyreport.scheduling.infrastructure.scheduler;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Component
public class InMemoryScheduledJobsRepository {
    private final Map<Long, ScheduledFuture<?>> jobsMap = new ConcurrentHashMap<>();

    public void save(Long taskId, ScheduledFuture<?> scheduledFuture) {
        jobsMap.put(taskId, scheduledFuture);
    }

    public Optional<ScheduledFuture<?>> findById(Long taskId) {
        return Optional.ofNullable(jobsMap.get(taskId));
    }

    public void remove(Long taskId) {
        jobsMap.remove(taskId);
    }
}
