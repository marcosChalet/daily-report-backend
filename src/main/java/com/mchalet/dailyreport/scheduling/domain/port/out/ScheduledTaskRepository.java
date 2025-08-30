package com.mchalet.dailyreport.scheduling.domain.port.out;

import com.mchalet.dailyreport.scheduling.domain.ScheduledTask;

import java.util.List;
import java.util.Optional;

public interface ScheduledTaskRepository {
    /**
     * Business Need: "I need to find a specific task by its unique ID."
     * @param taskId The id of the scheduled task to find.
     * @return an Optional containing the task if found, or an empty Optional otherwise.
     */
    Optional<ScheduledTask> findById(Long taskId);

    /**
     * Business Need: "I need to save a new task."
     * Persists a new scheduled task or updates an existing one (upsert behavior).
     * @param task The Task domain object to be saved.
     * @return The persisted task, which may contain new data like a generated ID.
     */
    ScheduledTask save(ScheduledTask task);

    /**
     * Business Need: "I need a complete list of all scheduled tasks."
     * Retrieves all tasks.
     * @return A List containing all scheduled tasks.
     */
    List<ScheduledTask> findAll();

    /**
     * Business Need: "I need a complete list of all scheduled tasks that not was processed."
     * Retrieves all active tasks.
     * @return A List containing all active scheduled tasks.
     */
    List<ScheduledTask> findAllActive();
}
