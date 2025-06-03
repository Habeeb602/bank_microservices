package com.microservice.loans.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class LoansDto {

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should consists of 10 digits")
    private String mobileNumber;

    @Pattern(regexp = "^[0-9]{12}$", message = "Loan number should consists of 12 digits")
    private String loanNumber;

    @Pattern(regexp = "Home Loan|Vehicle Loan", message = "Loan type should be either 'Home Loan' or 'Vehicle Loan'")
    private String loanType;

    @Min(value = 0, message = "Total loan can should the minimum value at least 0")
    @Max(value = 30000, message = "Total loan can have the maximum value of 30000")
    private Integer totalLoan;

    @Min(value = 0, message = "Amount Paid can should the minimum value at least 0")
    @Max(value = 30000, message = "Amount Paid can have the maximum value of 30000")
    private Integer amountPaid;

    @Min(value = 0, message = "Outstanding Amount can should the minimum value at least 0")
    @Max(value = 30000, message = "Outstanding Amount can have the maximum value of 30000")
    private Integer outstandingAmount;
}
