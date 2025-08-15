package com.mchalet.dailyreport.infrastructure.web.controller;

import com.mchalet.dailyreport.application.report.dto.CreateReportRequest;
import com.mchalet.dailyreport.application.report.dto.ReportFilter;
import com.mchalet.dailyreport.application.report.dto.ReportResponse;
import com.mchalet.dailyreport.application.report.dto.UpdateReportRequest;
import com.mchalet.dailyreport.application.report.service.ReportService;
import com.mchalet.dailyreport.domain.report.Report;
import com.mchalet.dailyreport.domain.shared.vo.ID;

import com.mchalet.dailyreport.infrastructure.mapper.ReportModelAssembler;
import com.mchalet.dailyreport.infrastructure.web.exception.ErrorDetail;
import com.mchalet.dailyreport.infrastructure.mapper.ReportMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Reports", description = "Endpoints for managing daily reports")
public class ReportController {
    private final ReportService reportService;
    private final ReportModelAssembler assembler;
    private final ReportMapper mapper;

    public ReportController(ReportService reportService, ReportModelAssembler assembler, ReportMapper mapper) {
        this.reportService = reportService;
        this.assembler = assembler;
        this.mapper = mapper;
    }

    @Operation(summary = "Create a new report", description = "Creates a new report with an optional list of tags.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Report created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., blank title)",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
    })
    @PostMapping("/reports")
    public ResponseEntity<EntityModel<ReportResponse>> createReport(@RequestBody @Valid CreateReportRequest request) {
        Report createdReport = reportService.createReport(request);
        ReportResponse reportResponse = mapper.toResponse(createdReport);
        EntityModel<ReportResponse> entityModel = assembler.toModel(reportResponse);
        URI location = entityModel.getRequiredLink("self").toUri();
        return ResponseEntity.created(location).body(entityModel);
    }

    @Operation(summary = "Find all reports", description = "Returns a collection of reports with its HATEOAS links.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report found successfully"),
    })
    @GetMapping("/reports")
    public ResponseEntity<PagedModel<EntityModel<ReportResponse>>> getAllReports(
            ReportFilter filter,

            @PageableDefault(page = 0, size = 20, sort = "updatedAt", direction = Sort.Direction.DESC)
            Pageable pageable,

            PagedResourcesAssembler<ReportResponse> pagedAssembler
    ) {
        Page<Report> reportPage = reportService.getAllReports(filter, pageable);
        Page<ReportResponse> responsePage = reportPage.map(mapper::toResponse);
        PagedModel<EntityModel<ReportResponse>> pagedModel = pagedAssembler.toModel(responsePage, assembler);
        return ResponseEntity.ok(pagedModel);
    }

    @Operation(summary = "Find a report by its ID", description = "Returns a single report with its HATEOAS links.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Report not found for the given ID",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @GetMapping("/reports/{reportId}")
    public ResponseEntity<EntityModel<ReportResponse>> getReportById(
            @Parameter(description = "UUID of the report to be retrieved", required = true)
            @PathVariable UUID reportId
    ) {
        Report report = reportService.getReportById(ID.from(reportId));
        ReportResponse reportResponse = mapper.toResponse(report);
        EntityModel<ReportResponse> entityModel = assembler.toModel(reportResponse);
        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "Update a report by its ID", description = "Returns a single report with new data and HATEOAS links.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. Check the request body.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "404", description = "Report not found for the given ID",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class))),
            @ApiResponse(responseCode = "409", description = "Business rule conflict")
    })
    @PutMapping("/reports/{reportId}")
    public ResponseEntity<EntityModel<ReportResponse>> updateReport(
            @Parameter(description = "UUID of the report to be updated", required = true)
            @PathVariable UUID reportId,
            @RequestBody @Valid UpdateReportRequest request) {
        Report updatedReport = reportService.updateReport(ID.from(reportId), request);
        ReportResponse reportResponse = mapper.toResponse(updatedReport);
        EntityModel<ReportResponse> entityModel = assembler.toModel(reportResponse);
        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "Delete a report", description = "Deletes a report by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Report deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Report not found for the given ID",
                    content = @Content(schema = @Schema(implementation = ErrorDetail.class)))
    })
    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<Void> deleteReport(
            @Parameter(description = "UUID of the report to be deleted", required = true)
            @PathVariable UUID reportId) {
        reportService.deleteReportById(ID.from(reportId));
        return ResponseEntity.noContent().build();
    }
}
