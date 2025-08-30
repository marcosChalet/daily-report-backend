package com.mchalet.dailyreport.scheduling.infrastructure.scheduler;

import com.mchalet.dailyreport.scheduling.domain.port.in.ScheduleTaskUseCase;
import com.mchalet.dailyreport.scheduling.domain.port.out.ScheduledTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class TaskLoader {
    private final ScheduledTaskRepository taskRepository;
    private final ScheduleTaskUseCase scheduleTaskUseCase;

    @EventListener(ContextRefreshedEvent.class)
    @Transactional(readOnly = true)
    public void scheduleAllTasksOnStartup(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            log.info("Loading and scheduling all tasks from the database...");
            taskRepository.findAllActive().forEach(scheduleTaskUseCase::execute);
            log.info("All tasks have been loaded and scheduled.");
        }
    }
}
