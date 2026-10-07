package com.hellgardd.filmarchive.controller;

import com.hellgardd.filmarchive.exception.*;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.ui.Model;

import org.springframework.validation.BindException;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

 private final Logger log =
         LoggerFactory.getLogger(GlobalExceptionHandler.class);

 private Object error(
         HttpServletRequest request,
         Model model,
         HttpStatus status,
         String message
 ) {
  if (request.getRequestURI().endsWith("/get_countries")) {
   return ResponseEntity
           .status(status)
           .body(Map.of("message", message));
  }

  model.addAttribute("status", status.value());
  model.addAttribute("errorMessage", message);

  return "error";
 }

 @ExceptionHandler({
         NotFoundException.class,
         NoResourceFoundException.class
 })
 @ResponseStatus(HttpStatus.NOT_FOUND)
 public Object notFound(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  return error(
          request,
          model,
          HttpStatus.NOT_FOUND,
          "Запрошенная запись или страница не найдена"
  );
 }

 @ExceptionHandler({
         FormValidationException.class,
         BindException.class,
         ConstraintViolationException.class,
         MethodArgumentTypeMismatchException.class,
         MissingServletRequestParameterException.class,
         HttpMessageNotReadableException.class
 })
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 public Object invalid(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  return error(
          request,
          model,
          HttpStatus.BAD_REQUEST,
          "Некорректные данные. Проверьте значения полей и повторите запрос."
  );
 }

 @ExceptionHandler(DataIntegrityViolationException.class)
 @ResponseStatus(HttpStatus.CONFLICT)
 public Object integrity(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  log.warn("Database constraint violation", ex);

  return error(
          request,
          model,
          HttpStatus.CONFLICT,
          "Данные уже существуют или связаны с другой записью. " +
                  "Обновите страницу и повторите действие."
  );
 }

 @ExceptionHandler({
         DataAccessException.class,
         PersistenceException.class
 })
 @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
 public Object database(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  log.error("Database operation failed", ex);

  return error(
          request,
          model,
          HttpStatus.SERVICE_UNAVAILABLE,
          "PostgreSQL временно недоступен. " +
                  "Проверьте подключение к базе данных."
  );
 }

 @ExceptionHandler(Exception.class)
 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
 public Object unexpected(
         Exception ex,
         HttpServletRequest request,
         Model model
 ) {
  log.error("Request failed", ex);

  return error(
          request,
          model,
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Не удалось выполнить запрос. Повторите попытку позже."
  );
 }
}