package com.example.game.card.card.service;

import com.example.game.card.card.entity.Card;
import com.example.game.card.card.entity.CardType;
import com.example.game.card.card.repository.CardRepository;
import com.example.game.card.enemycard.entity.EnemyCard;
import com.example.game.card.itemcard.entity.ItemCard;
import com.example.game.field.entity.Field;
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
    public List<Card> findAllByGameId(Integer id) {return cardRepository.findCardsByGameId(id);}
    public Card findCardByGameIdAndCardId(Integer gameId, Integer cardId) {return cardRepository.findCardByGameIdAndId(gameId, cardId);}

    public List<Card> findAll() {return cardRepository.findAll();}
    @Transactional
    public void deleteById(Integer id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new RuntimeException("No card found"));
        cardRepository.deleteById(id);
    }
}
