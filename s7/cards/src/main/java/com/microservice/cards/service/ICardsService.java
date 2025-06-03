package com.microservice.cards.service;

import com.microservice.cards.dto.CardsDto;

public interface ICardsService {

    void createCard(String mobileNumber);
    CardsDto fetchCard(String mobileNumber);
    void deleteCard(String mobileNumber);
    void updateCard(CardsDto cardsDto, String cardNumber);
}
