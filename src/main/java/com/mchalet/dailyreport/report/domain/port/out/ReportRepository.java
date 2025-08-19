package com.mchalet.dailyreport.report.domain.port.out;

import com.mchalet.dailyreport.report.application.dto.ReportFilter;
import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReportRepository {

    /**
     * Business Need: "I need to save a new or a modified report."
     * Persists a new report or updates an existing one (upsert behavior).
     * @param report The Report domain object to be saved.
     * @return The persisted Report, which may contain new data like a generated ID.
     */
    Report save(Report report);

    /**
     * Business Need: "I need a complete list of all reports."
     * Retrieves all Report entities. Use with caution in systems with many records.
     * Consider paginated alternatives for most use cases.
     * @return A List containing all reports.
     */
    Page<Report> findAll(ReportFilter filter, Pageable pageable);

    /**
     * Business Need: "I need to find a specific report by its unique ID."
     * @param reportId The ID (Value Object) of the report to find.
     * @return an Optional containing the Report if found, or an empty Optional otherwise.
     */
    Optional<Report> findById(ID reportId);

    /**
     * Business Need: "I need to delete a report."
     * Deletes a report from the persistence layer based on its ID.
     * @param reportId The ID (Value Object) of the report to be deleted.
     */
    void deleteById(ID reportId);

    /**
     * Business Need: "I need to find if exist a report."
     * Find a report from the persistence layer based on its ID.
     * @param reportId The ID (Value Object) of the report to be find.
     */
    boolean existsById(ID reportId);
}