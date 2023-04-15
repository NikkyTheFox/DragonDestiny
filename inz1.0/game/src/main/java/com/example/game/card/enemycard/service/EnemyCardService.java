package com.example.game.card.enemycard.service;

import com.example.game.card.card.entity.Card;
import com.example.game.card.card.service.CardService;
import com.example.game.card.enemycard.entity.EnemyCard;
import com.example.game.card.enemycard.repository.EnemyCardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnemyCardService {
    private EnemyCardRepository enemyCardRepository;

    @Autowired
    public EnemyCardService(EnemyCardRepository enemyCardRepository)
    {
        this.enemyCardRepository = enemyCardRepository;
    }

    public EnemyCard findById(Integer id) {
        Optional<EnemyCard> enemyCard = enemyCardRepository.findById(id);
        if (enemyCard.isPresent()) { // found
            return enemyCard.get();
        } else throw new RuntimeException("No enemy card found");
    }
    public List<EnemyCard> findAll() {return enemyCardRepository.findAll();}
    @Transactional
    public EnemyCard save(EnemyCard enemyCard) {
        return enemyCardRepository.save(enemyCard);
    }
    @Transactional
    public void deleteById(Integer id) {
        EnemyCard enemyCard = enemyCardRepository.findById(id).orElseThrow(() -> new RuntimeException("No enemy card found"));
        enemyCardRepository.deleteById(id);
    }
    @Transactional
    public EnemyCard update(Integer id, EnemyCard enemyCardRequest) {
        EnemyCard enemyCard = enemyCardRepository.findById(id).orElseThrow(() -> new RuntimeException("No enemy card found"));
        // ????
        enemyCard.setInitialHealth(enemyCardRequest.getInitialHealth());
        enemyCard.setInitialStrength(enemyCardRequest.getInitialStrength());
        return enemyCardRepository.save(enemyCard);
    }
}
