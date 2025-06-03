package com.microservice.accounts.controller;


import com.microservice.accounts.constants.AccountsConstants;
import com.microservice.accounts.dto.CustomerDto;
import com.microservice.accounts.dto.ErrorResponseDto;
import com.microservice.accounts.dto.ResponseDto;
import com.microservice.accounts.service.IAccountsService;
import com.microservice.accounts.dto.AccountsContactDetailsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@NoArgsConstructor
@Tag(
        name = "CRUD Rest services for YesBank",
        description = "This Rest Service provides create, retrieve, update, and delete APIs"
)
public class AccountsController {

    private IAccountsService iAccountsService;

    @Autowired
    public AccountsController(IAccountsService iAccountsService){
        this.iAccountsService = iAccountsService;
    }

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactDetailsDto accountsContactDetailsDto;

    @Value("${build.version}")
    private  String buildVersion;

    @PostMapping("/create")
    @Operation(
            summary = "To create an account with customer",
            description = "blah blah blah"
    )
    @ApiResponse(
            responseCode = AccountsConstants.STATUS_201,
            description = AccountsConstants.MESSAGE_201
    )
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){

        iAccountsService.create(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }


    @GetMapping("/fetch")
    @Operation(
            summary = "To fetch an customer with phone number",
            description = "blah blah blah"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Customer object data"
    )
    public ResponseEntity<CustomerDto> fetchCustomer(
                                                    @RequestParam
                                                    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be of 10 digits")
                                                    String mobileNumber){

        CustomerDto customerDto = iAccountsService.fetchCustomer(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @PutMapping("/updateAccount")
    @Operation(
            summary = "To update an account",
            description = "blah blah blah"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Customer/Account updated!"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = AccountsConstants.MESSAGE_417_UPDATE,
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
    }
    )
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = iAccountsService.updateAccount(customerDto);

        return isUpdated
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200))
                : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));

    }

    @DeleteMapping("/deleteAccount")
    @Operation(
            summary = "To delete an account/customer",
            description = "blah blah blah"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Customer/Account deleted!"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = AccountsConstants.MESSAGE_417_DELETE
            ),
    }
    )
    public ResponseEntity<ResponseDto> deleteAccount(
                                                    @RequestParam
                                                    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be of 10 digits")
                                                    String mobileNumber){

        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);


        return isDeleted
                ? ResponseEntity.status(204).body(new ResponseDto("204", "Account Deleted"))
                : ResponseEntity.status(404).body(new ResponseDto("417", AccountsConstants.MESSAGE_417_DELETE));
    }


    @GetMapping("/build-version")
    @Operation(
            summary = "To fetch the build info"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "blah blah blah"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(summary = "Get the Java version", description = "Blah blah blah")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }


    @Operation(summary = "Get the accounts contact info", description = "Blah blah blah")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactDetailsDto> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactDetailsDto);
    }



}
