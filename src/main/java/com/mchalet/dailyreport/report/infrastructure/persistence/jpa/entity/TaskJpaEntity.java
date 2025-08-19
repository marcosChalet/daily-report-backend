package com.mchalet.dailyreport.report.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class TaskJpaEntity {
    @Id
    private UUID id;
    private String value;
}
