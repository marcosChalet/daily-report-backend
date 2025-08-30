package com.mchalet.dailyreport.scheduling.domain.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

@Embeddable
public final class ValidityPeriod {

    @Enumerated(EnumType.STRING)
    private final Frequency frequency;
    private final java.time.LocalTime timeOfDay;
    private final java.time.LocalDate expirationDate;

    protected ValidityPeriod() {
        this.frequency = null;
        this.timeOfDay = null;
        this.expirationDate = null;
    }

    public ValidityPeriod(ExecutionSchedule schedule, ExpirationDate expirationDate) {
        this.frequency = schedule.frequency();
        this.timeOfDay = schedule.timeOfDay();
        this.expirationDate = expirationDate.value();
    }

    private ExecutionSchedule getSchedule() {
        return new ExecutionSchedule(frequency, timeOfDay);
    }

    private ExpirationDate getExpirationDate() {
        return new ExpirationDate(expirationDate);
    }

    public LocalDateTime calculateNextExecutionTime(LocalDateTime from) {
        return getSchedule().calculateNextExecutionTime(from);
    }

    public boolean isActiveOn(LocalDateTime dateTime) {
        return !getExpirationDate().isBefore(dateTime.toLocalDate());
    }
}
