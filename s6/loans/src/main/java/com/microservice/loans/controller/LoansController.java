package com.microservice.loans.controller;


import com.microservice.loans.dto.LoansContactDetailsInfo;
import com.microservice.loans.dto.LoansDto;
import com.microservice.loans.dto.ResponseDto;
import com.microservice.loans.service.ILoansService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LoansController {

    ILoansService iLoansService;

    public LoansController(ILoansService iLoansService){
        this.iLoansService = iLoansService;
    }

    @Autowired
    private LoansContactDetailsInfo loansContactDetailsInfo;

    @Value("${build.version}")
    private String buildVersion;


    @PostMapping(value = "/create")
    public ResponseEntity<ResponseDto> create(@RequestParam
                                              @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should consists of 10 digits")
                                              String mobileNumber){

        iLoansService.create(mobileNumber);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Loan object created successfully"));
    }

    @GetMapping(value = "/fetch")
    public ResponseEntity<LoansDto> fetch(@RequestParam
                                              @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should consists of 10 digits")
                                              String mobileNumber){
        LoansDto loansDto = iLoansService.fetch(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansDto);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<ResponseDto> delete(@RequestParam
                                              @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should consists of 10 digits")
                                              String mobileNumber){
        iLoansService.delete(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto("200", String.format("Loan associated with %s is deleted successfully", mobileNumber)));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ResponseDto> update(@RequestParam
                                                  @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should consists of 10 digits")
                                                  String mobileNumber,
                                              @RequestBody @Valid LoansDto loansDto){
        iLoansService.update(mobileNumber, loansDto);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResponseDto("202", "Loan updated successfully"));
    }

    @GetMapping("/build-version")
    public ResponseEntity<String> getBuildVersion(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactDetailsInfo> getContactInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansContactDetailsInfo);
    }

}
