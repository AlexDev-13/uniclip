package com.company.uniclip.backend.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message)  {
        super(message);
    }
}
