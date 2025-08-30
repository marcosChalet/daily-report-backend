package com.mchalet.dailyreport.scheduling.infrastructure.scheduler;

import com.mchalet.dailyreport.shared.domain.exception.ResourceNotFoundException;
import com.mchalet.dailyreport.scheduling.application.actions.TaskAction;
import com.mchalet.dailyreport.scheduling.domain.ScheduledTask;
import com.mchalet.dailyreport.scheduling.domain.port.in.ScheduleTaskUseCase;
import com.mchalet.dailyreport.scheduling.domain.port.out.ScheduledTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;

@Log4j2
@RequiredArgsConstructor
public class TaskRunner implements Runnable {
    private final Long taskId;
    private final ScheduleTaskUseCase scheduleTaskUseCase;
    private final ApplicationContext context;

    @Override
    public void run() {

        ScheduledTaskRepository taskRepository = context.getBean(ScheduledTaskRepository.class);

        try {
            ScheduledTask task = taskRepository.findById(this.taskId)
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found: " + this.taskId));

            String actionBeanName = task.getActionBeanName();
            TaskAction action = context.getBean(actionBeanName, TaskAction.class);

            log.info("Invoking action '{}' for task '{}'", actionBeanName, task.getName());
            action.execute(task.getParameters());

            task.scheduleNextExecution(task.getNextExecutionTime());
            ScheduledTask updatedTask = taskRepository.save(task);
            scheduleTaskUseCase.execute(updatedTask);

        } catch(Exception e) {
            log.error("Error executing task {}: {}", taskId, e.getMessage());
            // Add logic here to handle failures (e.g., retry, disable task, etc.)
        }
    }
}
