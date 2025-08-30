package com.mchalet.dailyreport.scheduling.infrastructure.persistence.jpa.entity;

import com.mchalet.dailyreport.scheduling.domain.vo.ValidityPeriod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;

@Log4j2
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Scheduled_Tasks")
@EntityListeners(AuditingEntityListener.class)
public class ScheduledTaskJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String parameters;

    @Column(nullable = false)
    private String actionBeanName;

    @Embedded
    private ValidityPeriod validityPeriod;

    private LocalDateTime nextExecutionTime;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public ScheduledTaskJpaEntity(Long id, String name, String actionBeanName, ValidityPeriod validityPeriod) {
        this.id = id;
        this.name = name;
        this.validityPeriod = validityPeriod;
        this.actionBeanName = actionBeanName;
        this.scheduleNextExecution(LocalDateTime.now());
    }

    /**
     * Calculates and sets the next execution time based on its validity period.
     * @param from The reference time to calculate the next execution from.
     */
    public void scheduleNextExecution(LocalDateTime from) {
        LocalDateTime nextTime = this.validityPeriod.calculateNextExecutionTime(from);

        if (this.validityPeriod.isActiveOn(nextTime)) {
            this.nextExecutionTime = nextTime;
        } else {
            this.nextExecutionTime = null;
            log.info("Task {} has expired and will no longer be scheduled.", name);
        }
    }

}
