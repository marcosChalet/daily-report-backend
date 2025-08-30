package com.mchalet.dailyreport.report.domain.vo;

import com.mchalet.dailyreport.report.domain.shared.constraints.ValidationConstants;
import com.mchalet.dailyreport.report.domain.shared.exception.BusinessRuleException;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;

import java.util.Objects;

public record Tag(ID id, String value) {
    public Tag {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Tag cannot be null or blank.");
        }
        if (value.length() > ValidationConstants.TAG_MAX_LENGTH) {
            throw new IllegalArgumentException("Tag cannot exceed " + ValidationConstants.TAG_PATTERN + " characters.");
        }
        if (!value.matches(ValidationConstants.TAG_PATTERN)) {
            throw new BusinessRuleException(
                    "Tag can only contain letters, numbers, and hyphens ('-'), " +
                            "cannot have consecutive hyphens, and cannot start or end with a hyphen."
            );
        }
    }

    /**
     * Static factory method to create a NEW Tag from user input.
     * The ID is intentionally null, as it has not been persisted yet.
     * This is typically used by a Mapper when converting from a DTO.
     *
     * @param value The string content of the tag.
     * @return A new Tag instance with a null ID.
     */
    public static Tag create(String value) {
        return new Tag(null, value.toLowerCase().trim()); // Normalizes the value
    }

    /**
     * Static factory method to reconstitute an EXISTING Tag from a data source (e.g., database).
     * This method implies that all data, including the ID, is already valid and known.
     *
     * @param id The existing ID of the tag.
     * @param value The existing value of the tag.
     * @return A Tag instance representing an existing record.
     */
    public static Tag with(ID id, String value) {
        Objects.requireNonNull(id, "ID cannot be null for an existing Tag.");
        return new Tag(id, value);
    }
}
