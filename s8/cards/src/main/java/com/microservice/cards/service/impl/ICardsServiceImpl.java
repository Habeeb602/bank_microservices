package com.microservice.cards.service.impl;

import com.microservice.cards.constants.CardsConstants;
import com.microservice.cards.dto.CardsDto;
import com.microservice.cards.entity.Cards;
import com.microservice.cards.exception.CardAlreadyExistsException;
import com.microservice.cards.exception.ResourceNotFoundException;
import com.microservice.cards.mapper.CardsMapper;
import com.microservice.cards.repository.CardsRepository;
import com.microservice.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ICardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> cardsOptional = cardsRepository.findByMobileNumber(mobileNumber);
        if(cardsOptional.isPresent()){
            throw new CardAlreadyExistsException(String.format("Card already exists with the given mobile number: %s", mobileNumber));
        }


        Cards card = new Cards();
        card.setMobileNumber(mobileNumber);
        populateCard(card);

        cardsRepository.save(card);
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber). orElseThrow(
                () -> new ResourceNotFoundException(String.format("No cards associated with the given mobile number: %s", mobileNumber))
        );

        return CardsMapper.cardsToCardsDto(cards, new CardsDto());
    }

    @Override
    public void deleteCard(String mobileNumber) {

        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException(String.format("No cards associated with the given mobile number: %s", mobileNumber))
        );

        cardsRepository.deleteById(cards.getCardId());
    }

    @Override
    public void updateCard(CardsDto cardsDto, String cardNumber) {

        Cards cards = cardsRepository.findByCardNumber(cardNumber).orElseThrow(
                () -> new ResourceNotFoundException(String.format("No card found with the given card number: %s", cardNumber))
        );

        CardsMapper.cardsDtoToCards(cardsDto, cards);

        cardsRepository.save(cards);
    }


    private void populateCard(Cards card){
        long cardNum = 100000000000L + new Random().nextLong(10000000000L);
        card.setCardNumber(Long.toString(cardNum));
        card.setCardType(CardsConstants.CARD_TYPE_CRED);
        card.setAvailableAmount(CardsConstants.CARD_LIMIT_CRED);
        card.setAmountUsed(0);
        card.setTotalLimit(CardsConstants.CARD_LIMIT_CRED);
    }
}
