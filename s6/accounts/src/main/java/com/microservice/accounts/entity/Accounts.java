package com.microservice.accounts.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Accounts extends BaseEntity{

    @Column(name = "customer_id")
    private Long customerId;

    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    // We may or may not mention the @Column, as it's exactly matching with the table field
    private String accountType;
    private String branchAddress;
}
