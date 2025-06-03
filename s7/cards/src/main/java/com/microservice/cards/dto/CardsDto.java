package com.microservice.cards.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CardsDto {
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should consists of 10 digits")
    private String mobileNumber;

    @Pattern(regexp = "^[0-9]{12}$", message = "Card number should consists of 12 digits")
    private String cardNumber;

    @Pattern(regexp = "DEBIT|CURRENT|CREDIT", message = "Card type should be either DEBIT or CURRENT or CREDIT")
    private String cardType;

    @Min(value = 0, message = "Total limit can should the minimum value at least 0")
    @Max(value = 30000, message = "Total limit can have the maximum value of 30000")
    private Integer totalLimit;

    @Min(value = 0, message = "Amount used should have the minimum value at least 0")
    @Max(value = 30000, message = "Amount used can have the maximum value of 30000")
    private Integer amountUsed;

    @Min(value = 0, message = "Available amount should have the minimum value at least 0")
    @Max(value = 30000, message = "Available amount can have the maximum value of 30000")
    private Integer availableAmount;
}
