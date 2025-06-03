package com.microservice.loans.service.impl;

import com.microservice.loans.constants.LoansConstants;
import com.microservice.loans.dto.LoansDto;
import com.microservice.loans.entity.Loans;
import com.microservice.loans.exception.DataAlreadyExistsException;
import com.microservice.loans.exception.ResourceNotFoundException;
import com.microservice.loans.mapper.LoansMapper;
import com.microservice.loans.repository.LoanRepository;
import com.microservice.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private LoanRepository loanRepository;

    @Override
    public void create(String mobileNumber) {

        if(loanRepository.findByMobileNumber(mobileNumber).isPresent()){
            throw(new DataAlreadyExistsException(String.format("This %s mobile number already has loan associated with", mobileNumber)));
        }
        Loans loans = new Loans();
        populateCard(loans, mobileNumber);

        loanRepository.save(loans);

    }

    @Override
    public LoansDto fetch(String mobileNumber) {

        Loans loans = loanRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("No loans found with this given mobile number: " + mobileNumber)
        );

        LoansDto loansDto = new LoansDto();

        LoansMapper.loansToLoansDto(loans, loansDto);

        return loansDto;
    }

    @Override
    public void update(String mobileNumber, LoansDto loansDto) {
        Loans loans = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("No loan found with %s mobile number", mobileNumber))
                );

        LoansMapper.loansDtoToLoan(loansDto, loans);

        loanRepository.save(loans);
    }

    @Override
    public void delete(String mobileNumber) {
        Loans loans = loanRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("No loan found with %s mobile number", mobileNumber))
                );

        loanRepository.delete(loans);
    }

    private void populateCard(Loans loans, String mobileNumber){
        loans.setMobileNumber(mobileNumber);
        long loanNum = 100000000000L + new Random().nextLong(10000000000L);
        loans.setLoanNumber(Long.toString(loanNum));
        loans.setLoanType(LoansConstants.VEHICLE_LOAN);
        loans.setTotalLoan(20000);
        loans.setOutstandingAmount(18000);
        loans.setAmountPaid(2000);
    }

    
}
