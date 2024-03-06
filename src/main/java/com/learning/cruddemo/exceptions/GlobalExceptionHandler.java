package com.learning.cruddemo.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(ResourceNotFoundException ex) {
        Object[] args = ex.getArgs();
        String message = messageSource.getMessage(ex.getMessage(), args, LocaleContextHolder.getLocale());

        CustomErrorResponse errorResponse = new CustomErrorResponse( ex.getTimestamp(), message);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) throws JsonProcessingException {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        CustomErrorResponse errorResponse = new CustomErrorResponse( new Date(), ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> userLoginException(UserLoginException ex, WebRequest request) {

        CustomErrorResponse errorResponse = new CustomErrorResponse( new Date(), ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> exceptionHandler(Exception ex, WebRequest request) {
        String message = ex.getMessage();
        String error = ex.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getClass().getSimpleName().equals("InternalAuthenticationServiceException"))
            status = HttpStatus.UNAUTHORIZED;
        if (ex.getClass().getSimpleName().equals("HttpMessageNotReadableException")) {
            status = HttpStatus.BAD_REQUEST;
            message = "Malformed JSON request format";
        }
        CustomErrorResponse errorResponse = new CustomErrorResponse( new Date(), message);

        return new ResponseEntity<>(errorResponse, status);
    }
}
