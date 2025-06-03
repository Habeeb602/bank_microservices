package com.microservice.loans.exception;


import com.microservice.loans.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleDataAlreadyExistsException(DataAlreadyExistsException exception, WebRequest request){
        ErrorResponseDto responseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.CONFLICT,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(responseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
        ErrorResponseDto responseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(responseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnknownException(ResourceNotFoundException exception, WebRequest request){
        ErrorResponseDto responseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseDto);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String,String> map = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(f -> {
            map.put(f.getField(), f.getDefaultMessage());
        });


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(map);
    }

}
