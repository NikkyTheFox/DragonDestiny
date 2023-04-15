package com.example.game.card.card.service;

import com.example.game.card.card.entity.Card;
import com.example.game.card.card.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository)
    {
        this.cardRepository = cardRepository;
    }

    public Card findById(Integer id) {
        Optional<Card> card = cardRepository.findById(id);
        if (card.isPresent()) { // found
            return card.get();
        } else throw new RuntimeException("No card found");
    }
    public List<Card> findAll() {return cardRepository.findAll();}
    @Transactional
    public Card save(Card card) {
        return cardRepository.save(card);
    }
    @Transactional
    public void deleteById(Integer id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new RuntimeException("No card found"));
        cardRepository.deleteById(id);
    }
    @Transactional
    public Card update(Integer id, Card cardRequest) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new RuntimeException("No card found"));
        card.setName(cardRequest.getName());
        card.setDescription(cardRequest.getDescription());
        return cardRepository.save(card);
    }


}