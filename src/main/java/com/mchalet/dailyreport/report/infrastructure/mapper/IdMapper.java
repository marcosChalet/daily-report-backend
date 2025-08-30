package com.mchalet.dailyreport.report.infrastructure.mapper;

import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * A shared mapper responsible for converting between the ID Value Object
 * and its primitive representation (UUID).
 * This provides a single source of truth for ID mapping.
 */
@Component
public interface IdMapper {
    public static ID toId(UUID uuid) {
        return uuid != null ? new ID(uuid) : null;
    }

    public static UUID toValue(ID id) {
        return id != null ? id.value() : null;
    }
}
