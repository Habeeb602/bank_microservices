package com.microservice.cards.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private String path;
    private HttpStatus errorCode;
    private String errorMessage;
    private LocalDateTime timeStamp;
}
