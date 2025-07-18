package com.microservice.accounts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


@ConfigurationProperties(prefix = "accounts")
@Getter
@Setter
public class AccountsContactDetailsDto {
    String message;
    Map<String,String> contactDetails;
    List<String> onCallSupport;
}
