package com.sema.scheduledemail.entity;

import com.sema.scheduledemail.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;


@Entity
@Table(
        name = "emails",
        indexes = {
                @Index(
                        name = "idx_emails_status_scheduled",
                        columnList = "status, scheduled_at"
                )
        }
)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String recipient;

    @Column(nullable = false)
    String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    String body;

    @Column
    Instant sentAt;

    @Column(columnDefinition = "TEXT")
    String errorMessage;

    @Column(nullable = false)
    int retryCount = 0;

    @Column(name = "next_retry_at")
    Instant nextRetryAt;

    @Column(name = "processing_started_at")
    Instant processingStartedAt;

    @Column(name = "scheduled_at", nullable = false)
    Instant scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    EmailStatus status = EmailStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;
}
