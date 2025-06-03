package com.microservice.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Response", description = "This object carries the status code and message of successful response.")
public class ResponseDto {

    @Schema(description = "Status code", example = "200")
    private String statusCode;
    @Schema(description = "Status message", example = "Request processed successfully")
    private String statusMessage;
}
