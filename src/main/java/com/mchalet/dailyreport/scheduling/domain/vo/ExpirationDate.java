package com.mchalet.dailyreport.scheduling.domain.vo;

import java.time.LocalDate;

public record ExpirationDate(LocalDate value) {

    public ExpirationDate {
        if (value == null) {
            throw new IllegalArgumentException("Expiration date cannot be null.");
        }
    }

    public boolean isAfter(LocalDate date) {
        return this.value.isAfter(date);
    }

    public boolean isBefore(LocalDate date) {
        return this.value.isBefore(date);
    }
}
