package com.sema.scheduledemail.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(Long id) {
        super("Email not found with id: " + id);
    }
}