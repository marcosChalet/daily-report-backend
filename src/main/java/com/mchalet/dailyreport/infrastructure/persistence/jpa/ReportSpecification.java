package com.mchalet.dailyreport.infrastructure.persistence.jpa;

import com.mchalet.dailyreport.application.report.dto.ReportFilter;
import com.mchalet.dailyreport.infrastructure.persistence.jpa.entity.ReportJpaEntity;
import com.mchalet.dailyreport.infrastructure.persistence.jpa.entity.TagJpaEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ReportSpecification {

    public static Specification<ReportJpaEntity> build(ReportFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // (LIKE %title%)
            if (StringUtils.hasText(filter.title())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + filter.title().toLowerCase() + "%"));
            }

            if (filter.type() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), filter.type()));
            }

            if (filter.dateFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filter.dateFrom()));
            }
            if (filter.dateTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), filter.dateTo()));
            }

            if (!CollectionUtils.isEmpty(filter.tags())) {
                Join<ReportJpaEntity, TagJpaEntity> tagJoin = root.join("tags");
                predicates.add(tagJoin.get("name").in(filter.tags()));
                query.distinct(true);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}