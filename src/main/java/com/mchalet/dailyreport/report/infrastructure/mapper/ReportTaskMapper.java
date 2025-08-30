package com.mchalet.dailyreport.report.infrastructure.mapper;

import com.mchalet.dailyreport.report.application.dto.ReportTaskRequest;
import com.mchalet.dailyreport.report.application.dto.ReportTaskResponse;
import com.mchalet.dailyreport.report.domain.ReportTask;
import com.mchalet.dailyreport.report.domain.shared.vo.ID;
import com.mchalet.dailyreport.report.infrastructure.persistence.jpa.entity.TaskJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReportTaskMapper {

    default ReportTask toDomain(ReportTaskRequest request) {
        if (request == null) {
            return null;
        }
        return ReportTask.create(request.value());
    }

    @Mapping(source = "id", target = "id", qualifiedByName = "IdToValue")
    ReportTaskResponse toResponse(ReportTask reportTask);

    default ReportTask toDomain(TaskJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return ReportTask.with(IdMapper.toId(entity.getId()), entity.getValue());
    }

    @Mapping(source = "id", target = "id", qualifiedByName = "IdToValue")
    TaskJpaEntity toEntity(ReportTask reportTask);

    List<ReportTaskResponse> toResponseList(List<ReportTask> reportTasks);

    List<ReportTask> toDomainList(List<TaskJpaEntity> entities);

    @Named("IdToValue")
    default UUID idToValue(ID id) {
        return IdMapper.toValue(id);
    }

    @Named("UuidToId")
    default ID uuidToId(UUID uuid) {
        return IdMapper.toId(uuid);
    }

    /**
     * Helper qualificado para converter ID para UUID.
     */
    @Named("IdToUuid")
    default UUID idToUuid(ID id) {
        return IdMapper.toValue(id);
    }
}