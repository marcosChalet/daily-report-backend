package com.mchalet.dailyreport.scheduling.domain.port.in;

import com.mchalet.dailyreport.scheduling.domain.ScheduledTask;

public interface ScheduleTaskUseCase {
    void execute(ScheduledTask task);
}
