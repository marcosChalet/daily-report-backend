package com.mchalet.dailyreport.scheduling.application.actions;

@FunctionalInterface
public interface TaskAction {
    void execute(String parameters);
}
