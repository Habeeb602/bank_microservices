package com.microservice.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(name = "Accounts", description = "Schema to carry accounts info")
public class AccountsDto {

    @NotEmpty(message = "Account number cannot be empty or null")
    @Pattern(regexp = "^[0-9]{10}$", message = "Account number should consists of 10 digits")
    @Schema(description = "Account Number", example = "7534924678")
    private Long accountNumber;

    @NotEmpty(message = "Account type cannot be empty or null")
    @Schema(description = "Account Type", example = "Savings")
    private String accountType;

    @NotEmpty(message = "Branch address cannot be empty or null")
    @Schema(description = "Bank branch address", example = "321, Main Street")
    private String branchAddress;
}
