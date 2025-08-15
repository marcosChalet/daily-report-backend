package com.mchalet.dailyreport.infrastructure.mapper;

import com.mchalet.dailyreport.application.report.dto.ReportTaskRequest;
import com.mchalet.dailyreport.application.report.dto.ReportTaskResponse;
import com.mchalet.dailyreport.domain.report.ReportTask;
import com.mchalet.dailyreport.domain.shared.vo.ID;
import com.mchalet.dailyreport.infrastructure.persistence.jpa.entity.TaskJpaEntity;
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