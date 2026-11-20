package com.sema.scheduledemail.repository;

import com.sema.scheduledemail.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

    @Query(value = """
        SELECT *
        FROM emails
        WHERE (
            status = 'PENDING'
            AND scheduled_at <= :now
        )
        OR (
            status = 'FAILED'
            AND next_retry_at <= :now
            AND retry_count < :maxRetries
        )
        OR (
            status = 'PROCESSING'
            AND processing_started_at <= :processingTimeout
            AND retry_count < :maxRetries
        )
        ORDER BY scheduled_at
        LIMIT :batchSize
        FOR UPDATE SKIP LOCKED
        """, nativeQuery = true)
    List<Email> findEmailsReadyToSend(
            @Param("now") Instant now,
            @Param("processingTimeout") Instant processingTimeout,
            @Param("maxRetries") int maxRetries,
            @Param("batchSize") int batchSize
    );


}
