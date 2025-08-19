package com.mchalet.dailyreport.report.infrastructure.mapper;

import com.mchalet.dailyreport.report.application.dto.TagRequest;
import com.mchalet.dailyreport.report.application.dto.TagResponse;
import com.mchalet.dailyreport.report.domain.vo.Tag;
import com.mchalet.dailyreport.report.infrastructure.persistence.jpa.entity.TagJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IdMapper.class})
public interface TagMapper {

    /**
     * Converts a request DTO into a new domain Tag, generating a new ID.
     */
    @Mapping(target = "id", expression = "java(com.mchalet.dailyreport.report.domain.shared.vo.ID.generate())")
    Tag toDomain(TagRequest request);

    /**
     * Converts a domain Tag into a response DTO.
     */
    @Mapping(source = "id", target = "id") // 'IdMapper' will be used here to convert ID -> UUID
    TagResponse toResponse(Tag tag);

    /**
     * Converts a JPA entity into a domain Tag.
     */
    Tag toDomain(TagJpaEntity entity);

    /**
     * Converts a domain Tag into a JPA entity for persistence.
     */
    @Mapping(source = "id", target = "id") // 'IdMapper' will be used here to convert ID -> UUID
    TagJpaEntity toEntity(Tag tag);

    /**
     * Converts a JPA List Tag into a Domain List of tags.
     */
    List<Tag> toDomainList(List<TagJpaEntity> entities);
}