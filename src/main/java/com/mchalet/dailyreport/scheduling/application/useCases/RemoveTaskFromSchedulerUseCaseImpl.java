package com.mchalet.dailyreport.scheduling.application.useCases;

import com.mchalet.dailyreport.scheduling.domain.port.in.RemoveTaskFromSchedulerUseCase;
import com.mchalet.dailyreport.scheduling.infrastructure.scheduler.InMemoryScheduledJobsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RemoveTaskFromSchedulerUseCaseImpl implements RemoveTaskFromSchedulerUseCase {
    private final InMemoryScheduledJobsRepository scheduledJobsRepository;

    @Override
    public void execute(Long taskId) {
        scheduledJobsRepository.findById(taskId).ifPresent(scheduledTask -> {
            scheduledTask.cancel(true);
            scheduledJobsRepository.remove(taskId);
        });
    }
}
