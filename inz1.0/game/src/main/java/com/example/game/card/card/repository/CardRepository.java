package com.example.game.card.card.repository;

import com.example.game.card.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {

    public List<Card> findCardsByGameId(Integer id);

    public Card findCardByGameIdAndId(Integer gameId, Integer id);
}
