package com.sema.scheduledemail.controller;

import com.sema.scheduledemail.dto.request.EmailRequest;
import com.sema.scheduledemail.dto.response.EmailResponse;
import com.sema.scheduledemail.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    @ResponseStatus(CREATED)
    public EmailResponse scheduleEmail(
            @Valid @RequestBody EmailRequest request) {
        return emailService.scheduleEmail(request);
    }


    @GetMapping("/{id}")
    public EmailResponse getEmailById(@PathVariable Long id) {

        return emailService.getEmailById(id);
    }

    @GetMapping
    public Page<EmailResponse> getAllEmails(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {

        return emailService.getAllEmails(pageable);
    }
}