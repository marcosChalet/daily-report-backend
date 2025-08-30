package com.mchalet.dailyreport.scheduling.domain.port.in;

public interface RemoveTaskFromSchedulerUseCase {
    void execute(Long taskId);
}
