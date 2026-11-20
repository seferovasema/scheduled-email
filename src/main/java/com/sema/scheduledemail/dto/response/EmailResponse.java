package com.sema.scheduledemail.dto.response;

import com.sema.scheduledemail.enums.EmailStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailResponse {

    Long id;

    String recipient;

    String subject;

    Instant scheduledAt;

    EmailStatus status;

    Instant sentAt;

    Instant createdAt;
}
