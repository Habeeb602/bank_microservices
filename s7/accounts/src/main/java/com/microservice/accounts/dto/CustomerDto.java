package com.microservice.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Customer", description = "Schema to carry customer info")
public class CustomerDto {
    @NotEmpty(message = "Customer name cannot be empty or null")
    @Size(min = 5, max = 30, message = "Customer name length should be between 5 to 30 characters")
    @Schema(description = "Name", example = "Habeeb Rahman")
    private String name;

    @NotEmpty(message = "Email cannot be empty or null")
    @Email
    @Schema(description = "email", example = "habeeb@gmail.com")
    private String email;

    @NotEmpty(message = "mobileNumber cannot be empty or null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be of 10 digits")
    @Schema(description = "Mobile Number", example = "9876567890")
    private String mobileNumber;

    @Schema(name = "Accounts")
    private AccountsDto accountsDto;
}
