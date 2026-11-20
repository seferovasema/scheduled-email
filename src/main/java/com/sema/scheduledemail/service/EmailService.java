package com.sema.scheduledemail.service;

import com.sema.scheduledemail.dto.request.EmailRequest;
import com.sema.scheduledemail.dto.response.EmailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailService {
    EmailResponse scheduleEmail(EmailRequest request);
    void processScheduledEmails();
    EmailResponse getEmailById(Long id);
    Page<EmailResponse> getAllEmails(Pageable pageable);
}
