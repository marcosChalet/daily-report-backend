package com.mchalet.dailyreport.scheduling.domain;

import com.mchalet.dailyreport.scheduling.domain.vo.ValidityPeriod;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduledTask {
    private Long id;
    private final String name;
    private final String parameters;
    private final String actionBeanName;
    private final ValidityPeriod validityPeriod;
    private LocalDateTime nextExecutionTime;


    public static ScheduledTask with(String name, String actionBeanName, String parameters, ValidityPeriod validityPeriod) {
        ScheduledTask task = new ScheduledTask(null, name, parameters, actionBeanName, validityPeriod, null);
        task.scheduleNextExecution(LocalDateTime.now());
        return task;
    }

    public void scheduleNextExecution(LocalDateTime from) {
        LocalDateTime nextTime = this.validityPeriod.calculateNextExecutionTime(from);

        if (this.validityPeriod.isActiveOn(nextTime)) {
            this.nextExecutionTime = nextTime;
        } else {
            this.nextExecutionTime = null;
        }
    }

    public void assignId(Long id) {
        if (this.id == null) {
            this.id = id;
        }
    }
}
