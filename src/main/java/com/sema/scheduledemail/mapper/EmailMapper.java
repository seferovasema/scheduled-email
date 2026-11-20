package com.sema.scheduledemail.mapper;

import com.sema.scheduledemail.dto.request.EmailRequest;
import com.sema.scheduledemail.dto.response.EmailResponse;
import com.sema.scheduledemail.entity.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "errorMessage", ignore = true)
    @Mapping(target = "retryCount", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "nextRetryAt", ignore = true)
    @Mapping(target = "processingStartedAt", ignore = true)
    Email toEntity(EmailRequest request);

    EmailResponse toResponse(Email email);
}
