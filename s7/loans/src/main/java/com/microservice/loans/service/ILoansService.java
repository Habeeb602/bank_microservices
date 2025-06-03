package com.microservice.loans.service;

import com.microservice.loans.dto.LoansDto;
import org.springframework.stereotype.Service;


public interface ILoansService {

    void delete(String mobileNumber);

    void create(String mobileNumber);

    LoansDto fetch(String mobileNumber);

    void update(String mobileNumber, LoansDto loansDto);
}
