package com.sema.scheduledemail.service.impl;

import com.sema.scheduledemail.dto.request.EmailRequest;
import com.sema.scheduledemail.dto.response.EmailResponse;
import com.sema.scheduledemail.entity.Email;
import com.sema.scheduledemail.enums.EmailStatus;
import com.sema.scheduledemail.exception.EmailNotFoundException;
import com.sema.scheduledemail.mapper.EmailMapper;
import com.sema.scheduledemail.repository.EmailRepository;
import com.sema.scheduledemail.service.EmailClaimService;
import com.sema.scheduledemail.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;
    private final JavaMailSender mailSender;
    private final EmailClaimService emailClaimService;

    @Override
    public EmailResponse scheduleEmail(EmailRequest request) {
        Email email = emailMapper.toEntity(request);
        Email savedEmail = emailRepository.save(email);
        return emailMapper.toResponse(savedEmail);
    }

    @Override
    public void processScheduledEmails() {

        List<Email> emails = emailClaimService.claimEmails();

        log.info("Found {} emails ready to send", emails.size());

        for (Email email : emails) {
            sendEmail(email);
        }
    }

    @Override
    public EmailResponse getEmailById(Long id) {

        Email email = emailRepository.findById(id)
                .orElseThrow(() ->new EmailNotFoundException(id));

        return emailMapper.toResponse(email);
    }

    @Override
    public Page<EmailResponse> getAllEmails(Pageable pageable) {

        return emailRepository.findAll(pageable)
                .map(emailMapper::toResponse);
    }

    private void sendEmail(Email email) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.getRecipient());
            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            mailSender.send(message);

        } catch (Exception exception) {
            email.setStatus(EmailStatus.FAILED);
            email.setErrorMessage(exception.getMessage());
            email.setRetryCount(email.getRetryCount() + 1);
            email.setNextRetryAt(Instant.now().plus(Duration.ofMinutes(1)));
            email.setProcessingStartedAt(null);

            emailRepository.save(email);

            log.error("Failed to send email. Email ID: {}", email.getId(), exception);
            return;
        }

        try {
            email.setStatus(EmailStatus.SENT);
            email.setSentAt(Instant.now());
            email.setErrorMessage(null);
            email.setNextRetryAt(null);
            email.setProcessingStartedAt(null);

            emailRepository.save(email);

            log.info("Email sent successfully. Email ID: {}", email.getId());

        } catch (Exception exception) {
            log.error("Email ID {} was SENT, but status update failed", email.getId(), exception);
        }
    }
}
