package com.sema.scheduledemail.service;

import com.sema.scheduledemail.entity.Email;
import com.sema.scheduledemail.enums.EmailStatus;
import com.sema.scheduledemail.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailClaimService {

    private final EmailRepository emailRepository;

    @Value("${email.scheduler.processing-timeout-minutes}")
    private long processingTimeoutMinutes;

    @Value("${email.scheduler.max-retries}")
    private int maxRetries;

    @Value("${email.scheduler.batch-size}")
    private int batchSize;

    @Transactional
    public List<Email> claimEmails() {

        Instant now = Instant.now();
        Instant processingTimeout = now.minus(Duration.ofMinutes(processingTimeoutMinutes));

        List<Email> emails = emailRepository.findEmailsReadyToSend(
                now,
                processingTimeout,
                maxRetries,
                batchSize
        );

        List<Email> claimedEmails = new ArrayList<>();

        for (Email email : emails) {

            if (email.getStatus() == EmailStatus.PROCESSING) {
                email.setRetryCount(email.getRetryCount() + 1);
            }

            if (email.getRetryCount() >= maxRetries) {
                email.setStatus(EmailStatus.FAILED);
                email.setProcessingStartedAt(null);
                email.setNextRetryAt(null);
                email.setErrorMessage("Maximum retry attempts reached.");
                continue;
            }

            email.setStatus(EmailStatus.PROCESSING);
            email.setProcessingStartedAt(now);

            claimedEmails.add(email);
        }

        emailRepository.saveAll(emails);

        return claimedEmails;
    }
}