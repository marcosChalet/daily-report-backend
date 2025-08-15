package com.mchalet.dailyreport.domain.shared.vo;

import java.util.UUID;

/**
 * Value Object que representa um identificador único e imutável.
 */
public record ID(UUID value) {

    public ID {
        if (value == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
    }

    /**
     * Factory to generate a new ramdom ID.
     * @return new instance of ID.
     */
    public static ID generate() {
        return new ID(UUID.randomUUID());
    }

    /**
     * Factory to generate a ID from String.
     * @param id in string format.
     * @return ID instance.
     */
    public static ID from(String id) {
        try {
            return new ID(UUID.fromString(id));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ID format.", e);
        }
    }

    /**
     * Factory to generate a ID from UUID.
     * @param id in string format.
     * @return ID instance.
     */
    public static ID from(UUID id) {
        try {
            return new ID(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ID format.", e);
        }
    }
}
