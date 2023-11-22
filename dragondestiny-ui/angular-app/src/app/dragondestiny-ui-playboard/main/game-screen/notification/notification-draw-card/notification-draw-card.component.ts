import { PlayedGameService } from './../../../../../services/played-game/played-game-service';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { Card as EngineCard} from './../../../../../interfaces/game-engine/card/card/card';
import { FightResult } from 'src/app/interfaces/played-game/fight-result/fight-result';
import { Subscription } from 'rxjs';
import { GameDataStructure } from 'src/app/interfaces/game-data-structure';
import { SharedService } from 'src/app/services/shared.service';
import { CardType } from 'src/app/interfaces/played-game/card/card/card-type';
import { ItemCard } from 'src/app/interfaces/played-game/card/item-card/item-card';
import { EnemyCard } from 'src/app/interfaces/played-game/card/enemy-card/enemy-card';
import { Card } from 'src/app/interfaces/played-game/card/card/card';
import { GameEngineService } from 'src/app/services/game-engine/game-engine.service';
import { ItemCardList } from 'src/app/interfaces/played-game/card/item-card/item-card-list';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { RoundState } from 'src/app/interfaces/played-game/round/round-state';

@Component({
  selector: 'app-notification-draw-card',
  templateUrl: './notification-draw-card.component.html',
  styleUrls: ['./notification-draw-card.component.css']
})
export class NotificationDrawCardComponent implements OnInit, OnDestroy{
  @Input() notificationData!: any;
  @Input() cardsDrawn!: number;
  @Input() dieData!: {fightEnemyCondition: boolean, rollValue: number}
  @Input() showDrawCardConditionBoolean!: boolean;
  @Output() actionFinished = new EventEmitter();
  @Output() rollFightCondition = new EventEmitter<boolean>();
  @Output() cardsDrawnChange = new EventEmitter<number>();

  requestStructure!: GameDataStructure;
  
  toDeleteSubscription: Subscription[] = [];

  cardDisplayCondition: boolean = false;
  cardToDisplay!: EngineCard;
  cardAttributes: number[] = [];
  handCondition: boolean = false;
  equipCondition: boolean = false;
  fightResult!: FightResult;
  fightResultCondition: boolean = false;

  playerRoll: number = 0;
  enemyRoll: number = 0;
  dataFetchFlag: boolean = false;

  constructor(private engineService: GameEngineService, private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(): void {
    this.requestStructure = this.shared.getRequest();
    this.fetchRound();
  }

  fetchRound(){
    this.toDeleteSubscription.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        if(data.roundState == RoundState.WAITING_FOR_CARD_DRAWN){
          this.dataFetchFlag = true;
        }
      })
    )
  }

  drawCard(){
    if(this.notificationData > this.cardsDrawn){
      this.cardsDrawn += 1;
      this.cardsDrawnChange.emit(this.cardsDrawn);
      this.handleDrawCard();
    }
  }

  handleDrawCard(){
    this.toDeleteSubscription.push(
      this.playedGameService.drawRandomCard(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: Card) => {
        this.showDrawCardConditionBoolean = false;
        this.cardDisplayCondition = true;
        if(data.cardType == CardType.ITEM_CARD){
          let c = data as ItemCard;
          this.cardAttributes.push(c.health);
          this.cardAttributes.push(c.strength);
          this.handleItemCard(c);
        }
        if(data.cardType == CardType.ENEMY_CARD){
          let c = data as EnemyCard;
          this.cardAttributes.push(c.health);
          this.cardAttributes.push(c.initialStrength);
          this.handleEnemyCard(c);
        }
      })
    );
  }

  handleItemCard(data: ItemCard){
    this.toDeleteSubscription.push(
      this.engineService.getCard(data.id).subscribe( (data: EngineCard) => {
        this.cardToDisplay = data;
        this.checkHandCards();
      })
    );
  }

  checkHandCards(){
    this.toDeleteSubscription.push(
      this.playedGameService.getCardsFromPlayerHand(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: ItemCardList) => {
        if(data.itemCardList.length < 5){
          this.equipCondition = true;
        }
        else{
          this.handCondition = true;
        }
      })
    )
  }

  equip(){
    this.toDeleteSubscription.push(
      this.playedGameService.moveItemCardFromDeckToPlayerHand(
        this.requestStructure.game!.id, 
        this.cardToDisplay!.id, 
        this.requestStructure.player!.login).subscribe( () => {
          this.shared.sendEquipItemCardClickEvent();
          this.reset();
          this.actionFinished.emit();
        }
      )
    )
  }

  discard(){
    this.actionFinished.emit();
  }

  handleEnemyCard(data: EnemyCard){
    this.toDeleteSubscription.push(
      this.engineService.getCard(data.id).subscribe( (data: EngineCard) => {
        this.cardToDisplay = data;
        this.rollFightCondition.emit(true); // show Roll Die Button in Parent Component
      })
    );
  }

  fightEnemy(){
    this.playerRoll = this.dieData.rollValue;
    this.toDeleteSubscription.push(
      this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: number) => {
        this.enemyRoll = data;
        this.handleFightAfterDraw();
      })
    )
  }

  handleFightAfterDraw(){
    this.toDeleteSubscription.push(
      this.playedGameService.handleFightWithEnemyCard(
        this.requestStructure.game!.id,
        this.requestStructure.player!.login,
        this.cardToDisplay.id,
      ).subscribe( (data: FightResult) => {
        this.fightResult = data;
        this.reset();
        this.fightResultCondition = true;
      })
    )
  }

  getTrophy(){
    this.shared.sendUpdateStatisticsEvent();
    this.reset();
    this.actionFinished.emit()
  }

  acceptLoss(){
    this.shared.sendUpdateStatisticsEvent();
    this.actionFinished.emit();
  }

  reset(){
    this.showDrawCardConditionBoolean = false;
    this.cardDisplayCondition = false;
    this.handCondition = false; 
    this.equipCondition = false;
    this.fightResultCondition = false;
    this.dieData = {fightEnemyCondition: false, rollValue: 0};
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s.unsubscribe();
    });
  }
}
