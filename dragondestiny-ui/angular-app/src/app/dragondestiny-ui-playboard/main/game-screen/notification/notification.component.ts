import { share } from 'rxjs';
import { Card as EngineCard} from './../../../../interfaces/game-engine/card/card/card';
import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {PlayedGameService} from "../../../../services/played-game/played-game-service";
import {SharedService} from "../../../../services/shared.service";
import {GameDataStructure} from "../../../../interfaces/game-data-structure";
import {FightResult} from "../../../../interfaces/played-game/fight-result/fight-result";
import {Card} from "../../../../interfaces/played-game/card/card/card";
import { Subscription } from 'rxjs';
import { GameEngineService } from 'src/app/services/game-engine/game-engine.service';
import { CardType } from 'src/app/interfaces/played-game/card/card/card-type';
import { ItemCard } from 'src/app/interfaces/played-game/card/item-card/item-card';
import { EnemyCard } from 'src/app/interfaces/played-game/card/enemy-card/enemy-card';
import { CardList } from 'src/app/interfaces/played-game/card/card/card-list';
import { ItemCardList } from 'src/app/interfaces/played-game/card/item-card/item-card-list';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit, OnChanges, OnDestroy{
  drawCardSubscription!: Subscription;
  playerFightSubscription!: Subscription;
  enemyFightSubscription!: Subscription;
  rollDieSubscription!: Subscription;
  tempSubscription!: Subscription;
  subscriptionToDelete: Subscription[] = [];

  requestStructure!: GameDataStructure;
  @Input() notificationType!: number;
  @Input() notificationData!: any;

  fightResult!: FightResult;
  cardToDisplay!: EngineCard;
  cardAttributes: number[] = [];
  rollValue: number = 0;
  playerRoll: number = 0;
  enemyRoll: number = 0;
  conditionBoolean: boolean = false;
  cardDisplayCondition: boolean = false;
  handCondition: boolean = false;
  equipCondition: boolean = false;
  rollCondition: boolean = false;
  fightEnemyCondition: boolean = false;
  fightResultCondition: boolean = false;

  cardsDrawn: number = 0;
  finishCondition: boolean = false;
  actionsDone: number = 0;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService, private engineService: GameEngineService) {
  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.handleNotifications();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.cardAttributes = [];
    this.handleNotifications();
  }

  checkFinishCondition(){
    if(this.cardsDrawn == this.notificationData){
      this.finishCondition = true;
    }
  }

  handleNotifications(){
    this.checkFinishCondition();
    if(!this.finishCondition){
      switch(this.notificationType){
        case 1:
          // this.handleDrawCard();
          this.conditionBoolean = true; // show card draw option
          break;
        case 2:
          this.handleFightPlayer();
          break;
        case 3:
          this.handleFightAfterDraw();
          break;
      }
    }
  }

  drawCard(){
    if(this.notificationData > this.cardsDrawn){
      this.cardsDrawn += 1;
      this.handleDrawCard();
    }
  }

  handleDrawCard(){
    this.drawCardSubscription = this.playedGameService.drawRandomCard(this.requestStructure.game!.id).subscribe( (data: Card) => {
      this.conditionBoolean = false;
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
    });
  }

  handleEnemyCard(data: EnemyCard){
    this.subscriptionToDelete.push(
      this.engineService.getCard(data.id).subscribe( (data: EngineCard) => {
        this.cardToDisplay = data;
        this.rollCondition = true; // show Fight Button
      })
    );
  }

  fightEnemy(){
    this.subscriptionToDelete.push(
      this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: number) => {
        this.playerRoll = data;
        this.subscriptionToDelete.push(
          this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: number) => {
            this.enemyRoll = data;
            this.handleFightAfterDraw();
          })
        )
      })
    )
  }

  handleItemCard(data: ItemCard){
    this.subscriptionToDelete.push(
      this.engineService.getCard(data.id).subscribe( (data: EngineCard) => {
        this.cardToDisplay = data;
        this.checkHandCards();
      })
    );
  }

  checkHandCards(){
    this.subscriptionToDelete.push(
      this.playedGameService.getCardsFromPlayerHand(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: ItemCardList) => {
        if(data.itemCardList.length < 5){
          this.equipCondition = true;
        }
        else{
          this.handCondition = true;
          this.actionsDone++;
        }
      })
    )
  }

  equip(){
    this.subscriptionToDelete.push(
      this.playedGameService.moveItemCardFromDeckToPlayerHand(this.requestStructure.game!.id, this.cardToDisplay!.id, this.requestStructure.player!.login).subscribe( () => {
        this.shared.sendEquipItemCardClickEvent();
        this.reset();
        this.handleNotifications();
        this.actionsDone++;
      })
    )
  }

  handleFightPlayer(){
    // ROLL A DIE
    let roll = 6;
    this.playerFightSubscription = this.playedGameService.handleFightWithPlayer(
      this.requestStructure.game!.id,
      this.requestStructure.player!.login,
      roll,
      this.notificationData
    ).subscribe( (data: FightResult) => {
    // Handle fightResult data to be displayed in html file.
    });
  }

  handleFightAfterDraw(){
    this.subscriptionToDelete.push(
      this.enemyFightSubscription = this.playedGameService.handleFightWithEnemyCard(
        this.requestStructure.game!.id,
        this.requestStructure.player!.login,
        this.playerRoll,
        this.cardToDisplay.id,
        this.enemyRoll
      ).subscribe( (data: FightResult) => {
        // Handle fightResult data to be displayed in html file.
        console.log(data);
        this.fightResult = data;
        this.reset();
        this.fightResultCondition = true;
      })
    )
  }

  getTrophy(){
    this.subscriptionToDelete.push(
      this.playedGameService.moveCardToPlayerTrophies(
        this.requestStructure.game!.id,
        this.requestStructure.player!.login,
        this.cardToDisplay.id
      ).subscribe( () => {
        this.shared.sendUpdateStatisticsEvent();
        this.reset();
        this.actionsDone++;
      })
    )
  }

  handleBlockTurn(){
  //   TO DISCUSS
  }

  rollDie(){
    this.rollDieSubscription = this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe((data: number) => {
      this.rollValue = data;
      this.fightEnemyCondition = true;
    });
  }

  continue(){
    this.reset();
    this.handleNotifications();
    this.actionsDone = 0;
  }

  close(){
    this.reset();
    this.shared.sendNotificationCloseEvent();
  }

  reset(){
    this.conditionBoolean = false;
    this.cardDisplayCondition = false;
    this.handCondition = false; 
    this.equipCondition = false;
    this.rollCondition = false;
    this.fightEnemyCondition = false;
    this.fightResultCondition = false;
    this.rollValue = 0;
  }

  ngOnDestroy(): void {
    this.subscriptionToDelete.forEach( (s: Subscription) => {
      s.unsubscribe();
    });
    this.rollDieSubscription?.unsubscribe();
    this.enemyFightSubscription?.unsubscribe();
    this.playerFightSubscription?.unsubscribe();
    this.drawCardSubscription?.unsubscribe();
  }
}
