package com.hellgardd.filmarchive.exception;

import java.util.Map;

public class FormValidationException extends RuntimeException {

 private final Map<String, String> errors;

 public FormValidationException(
         String field,
         String message
 ) {
  super(message);
  this.errors = Map.of(field, message);
 }

 public Map<String, String> getErrors() {
  return errors;
 }
}