package com.mchalet.dailyreport.report.infrastructure.web.controller;

import com.mchalet.dailyreport.report.application.dto.ReportResponse;
import com.mchalet.dailyreport.report.domain.port.in.AddTaskToReportUseCase;
import com.mchalet.dailyreport.report.domain.port.in.RemoveTaskFromReportUseCase;
import com.mchalet.dailyreport.report.domain.Report;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import com.mchalet.dailyreport.report.infrastructure.mapper.ReportMapper;
import com.mchalet.dailyreport.report.infrastructure.mapper.ReportModelAssembler;
import com.mchalet.dailyreport.report.infrastructure.web.exception.ErrorDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mchalet.dailyreport.report.application.dto.ReportTaskRequest;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "ReportsTask", description = "Endpoints for managing tasks in daily reports")
public class ReportTaskController {
    private final AddTaskToReportUseCase addTaskToReportUseCase;
    private final RemoveTaskFromReportUseCase removeTaskFromReportUseCase;
    private final ReportModelAssembler assembler;
    private final ReportMapper mapper;

    public ReportTaskController(
            AddTaskToReportUseCase addTaskToReportUseCase,
            RemoveTaskFromReportUseCase removeTaskFromReportUseCase,
            ReportModelAssembler assembler,
            ReportMapper mapper
    ) {
        this.addTaskToReportUseCase = addTaskToReportUseCase;
        this.removeTaskFromReportUseCase = removeTaskFromReportUseCase;
        this.assembler = assembler;
        this.mapper = mapper;
    }

    @Operation(summary = "Add task to report", description = "Returns a single task with its HATEOAS links.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add task successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid format",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Report not found for the given ID",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
	@PostMapping("/reports/{reportId}/tasks")
	public ResponseEntity<EntityModel<ReportResponse>> addTask(
            @Parameter(description = "UUID of the report to add task", required = true)
            @PathVariable UUID reportId,

            @Parameter(description = "Task data to add to report", required = true)
            @RequestBody @Valid ReportTaskRequest request
    ) {
        Report updatedReport = addTaskToReportUseCase.execute(ID.from(reportId), request);
        ReportResponse reportResponse = mapper.toResponse(updatedReport);
        EntityModel<ReportResponse> entityModel = assembler.toModel(reportResponse);
        URI location = entityModel.getRequiredLink("self").toUri();
        return ResponseEntity.created(location).body(entityModel);
	}

    @Operation(summary = "Delete task", description = "Deletes a task by its ID into report.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found for the given ID",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
	@DeleteMapping("/reports/{reportId}/tasks/{taskId}")
	public ResponseEntity<Void> deleteTask(
            @Parameter(description = "UUID of the report to delete task", required = true)
            @PathVariable UUID reportId,

            @Parameter(description = "UUID of the task to be deleted", required = true)
            @PathVariable UUID taskId
    ) {
        removeTaskFromReportUseCase.execute(ID.from(reportId), ID.from(taskId));
        return ResponseEntity.noContent().build();
    }
}
