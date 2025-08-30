package com.mchalet.dailyreport.scheduling.application.useCases;

import com.mchalet.dailyreport.scheduling.domain.ScheduledTask;
import com.mchalet.dailyreport.scheduling.domain.port.in.RemoveTaskFromSchedulerUseCase;
import com.mchalet.dailyreport.scheduling.domain.port.in.ScheduleTaskUseCase;
import com.mchalet.dailyreport.scheduling.infrastructure.scheduler.InMemoryScheduledJobsRepository;
import com.mchalet.dailyreport.scheduling.infrastructure.scheduler.TaskRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.ScheduledFuture;

@RequiredArgsConstructor
@Service
public class ScheduleTaskUseCaseImpl implements ScheduleTaskUseCase {
    private final ApplicationContext context;
    private final TaskScheduler taskScheduler;
    private final RemoveTaskFromSchedulerUseCase removeTaskFromSchedulerUseCase;
    private final InMemoryScheduledJobsRepository scheduledJobsRepository;

    @Override
    public void execute(ScheduledTask task) {
        removeTaskFromSchedulerUseCase.execute(task.getId());

        if (task.getNextExecutionTime() == null) {
            return;
        }

        TaskRunner runner = new TaskRunner(task.getId(), this, context);
        Instant executionInstant = task.getNextExecutionTime().atZone(ZoneId.systemDefault()).toInstant();

        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(runner, executionInstant);

        scheduledJobsRepository.save(task.getId(), scheduledFuture);
    }
}
