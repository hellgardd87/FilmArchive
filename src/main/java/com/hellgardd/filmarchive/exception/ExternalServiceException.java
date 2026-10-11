package com.hellgardd.filmarchive.exception;

public class ExternalServiceException extends RuntimeException {
 public ExternalServiceException(Throwable cause) { super("Сервис временно недоступен", cause); }
}
