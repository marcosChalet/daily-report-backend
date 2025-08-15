package com.mchalet.dailyreport.infrastructure.mapper;

import com.mchalet.dailyreport.application.report.dto.CreateReportRequest;
import com.mchalet.dailyreport.application.report.dto.ReportResponse;
import com.mchalet.dailyreport.domain.report.Report;
import com.mchalet.dailyreport.domain.report.ReportTask;
import com.mchalet.dailyreport.domain.report.vo.ReportType;
import com.mchalet.dailyreport.domain.report.vo.Tag;
import com.mchalet.dailyreport.domain.report.vo.Title;
import com.mchalet.dailyreport.infrastructure.persistence.jpa.entity.ReportJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract mapper class for Report entities.
 * Being an abstract class allows for dependency injection of other mappers
 * while still letting MapStruct generate implementations for abstract methods.
 */
@Mapper(
        componentModel = "spring",
        uses = {IdMapper.class, ReportTaskMapper.class, TagMapper.class}
)
public abstract class ReportMapper {

    // Injected mappers for handling collections
    @Autowired
    protected ReportTaskMapper reportTaskMapper;
    @Autowired
    protected TagMapper tagMapper;

    // --- DTO -> Domain ---
    /**
     * Converts a creation DTO into a new domain Report using the Builder pattern.
     * Implemented as a concrete method because it uses a complex creation pattern (Builder).
     */
    public Report toDomain(CreateReportRequest request) {
        if (request == null) {
            return null;
        }
        Title title = toTitle(request.title());
        ReportType type = toReportType(request.type());

//        List<ReportTask> tasks = request.tasks().stream().map(reportTaskMapper::toDomain).toList();
        List<ReportTask> tasks = new ArrayList<>();
        List<Tag> tags = request.tags().stream().map(tagMapper::toDomain).toList();

        return Report.builder(title, type)
                .withTasks(tasks)
                .withTags(tags)
                .build();
    }

    // --- Domain -> DTO ---
    /**
     * Converts a domain Report into a response DTO.
     * Declared as 'abstract' so MapStruct will generate the implementation.
     */
    @Mappings({
            @Mapping(source = "id.value", target = "id"),
            @Mapping(source = "title.value", target = "title"),
            @Mapping(source = "type.value", target = "type"),
    })
    public abstract ReportResponse toResponse(Report report);

    // --- Persistence -> Domain ---
    /**
     * Reconstructs a domain Report from a JPA entity.
     * Implemented as a concrete method because it uses injected mappers.
     */
    public Report toDomain(ReportJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        List<ReportTask> reportTasks = reportTaskMapper.toDomainList(entity.getTasks());
        List<Tag> tags = tagMapper.toDomainList(entity.getTags());

        return Report.with(
                IdMapper.toId(entity.getId()),
                toTitle(entity.getTitle()),
                toReportType(entity.getType()),
                tags,
                reportTasks,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    // --- Domain -> Persistence ---
    /**
     * Converts a domain Report into a JPA entity for persistence.
     * Declared as 'abstract' so MapStruct will generate the implementation.
     */
    @Mappings({
            @Mapping(source = "id.value", target = "id"),
            @Mapping(source = "title.value", target = "title"),
            @Mapping(source = "type.value", target = "type"),
    })
    public abstract ReportJpaEntity toEntity(Report report);


    // --- Helper Methods ---
    /**
     * Helper methods for Value Object conversion.
     * Declared as 'protected' as they are for internal use within this mapper.
     */
    protected Title toTitle(String title) {
        return title != null ? new Title(title) : null;
    }

    protected ReportType toReportType(Integer type) {
        return type != null ? new ReportType(type) : null;
    }
}