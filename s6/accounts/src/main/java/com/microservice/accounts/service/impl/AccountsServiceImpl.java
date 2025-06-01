package com.microservice.accounts.service.impl;

import com.microservice.accounts.constants.AccountsConstants;
import com.microservice.accounts.dto.AccountsDto;
import com.microservice.accounts.dto.CustomerDto;
import com.microservice.accounts.entity.Accounts;
import com.microservice.accounts.entity.Customer;
import com.microservice.accounts.exception.CustomerAlreadyExistsException;
import com.microservice.accounts.exception.ResourceNotFoundException;
import com.microservice.accounts.mapper.AccountsMapper;
import com.microservice.accounts.mapper.CustomerMapper;
import com.microservice.accounts.repository.AccountsRepository;
import com.microservice.accounts.repository.CustomerRepository;
import com.microservice.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void create(CustomerDto customerDto) {

        Optional<Customer> customers = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(customers.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with the given mobile number: " + customerDto.getMobileNumber());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);
        Accounts accounts = createAccount(savedCustomer);
        Accounts savedAccounts = accountsRepository.save(accounts);
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {

        // OldSchool way of fetching data
        Optional<Customer> OptCustomer = customerRepository.findByMobileNumber(mobileNumber);
        if(OptCustomer.isEmpty()){
            throw  new ResourceNotFoundException("Customer", "Mobile number", mobileNumber);
        }

        Customer customer = OptCustomer.get();

        // New way of doing
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account","customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean updated = false;
        if(customerDto != null){
            AccountsDto accountsDto = customerDto.getAccountsDto();

            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(customerDto.getAccountsDto(), accounts);
            accountsRepository.save(accounts);

            Customer customer = customerRepository.findById(accounts.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", accounts.getCustomerId().toString())
            );

            CustomerMapper.mapToCustomer(customerDto, customer);

            customerRepository.save(customer);

            updated = true;
        }

        return updated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));
        customerRepository.deleteById(customer.getCustomerId());
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        return true;
    }


    private static Accounts createAccount(Customer customer){
        Accounts accounts = new Accounts();
        accounts.setAccountNumber(1000000000L + new Random().nextInt(900000000));
        accounts.setCustomerId(customer.getCustomerId());
        accounts.setAccountType(AccountsConstants.SAVINGS);
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        return accounts;
    }

}
