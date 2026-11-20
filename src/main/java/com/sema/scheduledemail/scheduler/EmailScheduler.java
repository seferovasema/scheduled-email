package com.sema.scheduledemail.scheduler;

import com.sema.scheduledemail.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private final EmailService emailService;

    @Scheduled(cron = "${email.scheduler.cron}")
    public void checkScheduledEmails() {
        log.info("Checking scheduled emails...");
        emailService.processScheduledEmails();
    }
}
