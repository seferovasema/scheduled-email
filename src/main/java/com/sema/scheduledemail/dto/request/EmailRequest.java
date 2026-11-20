package com.sema.scheduledemail.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailRequest {

    @NotBlank
    @Email
    String recipient;

    @NotBlank
    String subject;

    @NotBlank
    String body;

    @NotNull
    @Future
    Instant scheduledAt;
}