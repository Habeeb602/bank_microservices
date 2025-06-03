package com.microservice.cards.controller;


import com.microservice.cards.constants.CardsConstants;
import com.microservice.cards.dto.CardsContactDetailsInfo;
import com.microservice.cards.dto.CardsDto;
import com.microservice.cards.dto.ResponseDto;
import com.microservice.cards.service.ICardsService;
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
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {

    public CardsController(ICardsService iCardsService, CardsContactDetailsInfo cardsContactDetailsInfo){
        this.iCardsService = iCardsService;
    }

    private final ICardsService iCardsService;

    @Autowired
    private CardsContactDetailsInfo cardsContactDetailsInfo;

    @Value("${build.version}")
    private String buildVersion;


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@RequestParam
                                              @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be of 10 digits")
                                              String mobileNumber){

        iCardsService.createCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.STATUS_201_MSG));

    }


    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetch(@RequestParam
                                          @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be of 10 digits")
                                          String mobileNumber){
        CardsDto cardsDto = iCardsService.fetchCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> delete(@RequestParam
                                              @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should be of 10 digits")
                                              String mobileNumber){
        iCardsService.deleteCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.STATUS_200,CardsConstants.STATUS_200_MSG));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> update(@RequestBody
                                              @Valid
                                              CardsDto cardsDto, @RequestParam @Pattern(regexp = "^[0-9]{12}$", message = "Card number should consists of 12 digits") String cardNumber){


        iCardsService.updateCard(cardsDto, cardNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.STATUS_200_MSG));
    }


    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactDetailsInfo> getContactInfo(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactDetailsInfo);
    }


    @GetMapping("/build-version")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

}
