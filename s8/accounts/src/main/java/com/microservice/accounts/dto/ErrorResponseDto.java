package com.microservice.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(name = "Error Response", description = "This object carries error response details")
public class ErrorResponseDto {

    @Schema(description = "Error api path")
    private String apiPath;
    @Schema(description = "Error code")
    private HttpStatus errorCode;
    @Schema(description = "Error message")
    private String errorMessage;
    @Schema(description = "Error timestamp")
    private LocalDateTime errorTime;
}
