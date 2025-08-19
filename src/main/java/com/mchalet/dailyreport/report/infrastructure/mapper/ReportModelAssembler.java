package com.mchalet.dailyreport.report.infrastructure.mapper;

import com.mchalet.dailyreport.report.application.dto.ReportResponse;
import com.mchalet.dailyreport.report.infrastructure.web.controller.ReportController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReportModelAssembler extends RepresentationModelAssemblerSupport<ReportResponse, EntityModel<ReportResponse>> {

    public ReportModelAssembler() {
        super(ReportController.class, (Class<EntityModel<ReportResponse>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<ReportResponse> toModel(ReportResponse reportDto) {
        UUID reportId = reportDto.id();

        EntityModel<ReportResponse> model = EntityModel.of(reportDto);

        model.add(linkTo(methodOn(ReportController.class).getReportById(reportId)).withSelfRel());
        model.add(linkTo(methodOn(ReportController.class).updateReport(reportId, null)).withRel("update"));
        model.add(linkTo(methodOn(ReportController.class).deleteReport(reportId)).withRel("delete"));

        return model;
    }
}
