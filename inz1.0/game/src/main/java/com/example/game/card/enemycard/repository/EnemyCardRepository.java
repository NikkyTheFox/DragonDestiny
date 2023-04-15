package com.example.game.card.enemycard.repository;

import com.example.game.card.card.entity.Card;
import com.example.game.card.enemycard.entity.EnemyCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository//???????????????
public interface EnemyCardRepository extends JpaRepository<EnemyCard, Integer> {
}
