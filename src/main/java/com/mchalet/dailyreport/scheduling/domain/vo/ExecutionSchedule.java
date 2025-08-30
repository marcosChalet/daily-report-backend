package com.mchalet.dailyreport.scheduling.domain.vo;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ExecutionSchedule(Frequency frequency, LocalTime timeOfDay) {

    public ExecutionSchedule {
        if (frequency == null || timeOfDay == null) {
            throw new IllegalArgumentException("Frequency and time of day are required.");
        }
    }

    /**
     * Factory method for creating a daily schedule.
     * @param timeOfDay The time of day for the task to run.
     * @return A new ExecutionSchedule instance.
     */
    public static ExecutionSchedule dailyAt(LocalTime timeOfDay) {
        return new ExecutionSchedule(Frequency.DAILY, timeOfDay);
    }

    public LocalDateTime calculateNextExecutionTime(LocalDateTime from) {
        LocalDateTime potentialNextTime = from.toLocalDate().atTime(this.timeOfDay);
        if (potentialNextTime.isBefore(from) || potentialNextTime.isEqual(from)) {
            return switch (frequency) {
                case DAILY -> potentialNextTime.plusMinutes(1);
                case WEEKLY -> potentialNextTime.plusWeeks(1);
                case MONTHLY -> potentialNextTime.plusMonths(1);
            };
        }
        return potentialNextTime;
    }
}
