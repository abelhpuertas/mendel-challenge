package com.mendel.challenge.configuration;

import com.mendel.challenge.dto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

        ApiError apiError = ApiError.create(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
