package com.microservice.loans.mapper;

import com.microservice.loans.dto.LoansDto;
import com.microservice.loans.entity.Loans;

public class LoansMapper {

    public static void loansToLoansDto(Loans loans, LoansDto loansDto){
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());
        loansDto.setAmountPaid(loans.getAmountPaid());
    }

    public static void loansDtoToLoan(LoansDto loansDto, Loans loans) {
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());
        loans.setAmountPaid(loansDto.getAmountPaid());
    }
}
