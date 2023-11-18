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
import { ItemCardList } from 'src/app/interfaces/played-game/card/item-card/item-card-list';
import { EnemyCardList } from 'src/app/interfaces/played-game/card/enemy-card/enemy-card-list';
import { Player } from 'src/app/interfaces/played-game/player/player';

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

  dieData: {fightEnemyCondition: boolean, rollValue: number} = {fightEnemyCondition: false, rollValue: 0}

  fightResult!: FightResult;
  cardToDisplay!: EngineCard;
  cardAttributes: number[] = [];
  
  playerRoll: number = 0;
  enemyRoll: number = 0;
  showDrawCardConditionBoolean: boolean = false;
  showFightEnemyCardConditionBoolean: boolean = false;
  cardDisplayCondition: boolean = false;
  handCondition: boolean = false;
  equipCondition: boolean = false;
  rollCondition: boolean = false;
  // fightEnemyCondition: boolean = false;
  fightResultCondition: boolean = false;
  bridgeFlag: boolean = false; // player is on bridge field
  bossRoomFlag: boolean = false; // player is on boss field

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
    this.requestStructure = this.shared.getRequest();
    this.cardAttributes = [];
    this.handleNotifications();
  }

  checkFinishCondition(){
    // Card draw check:
    if(this.cardsDrawn == this.notificationData){
      this.finishCondition = true;
    }
    // Field Enemy Fought Once
    // Player Fought Once
  }

  handleNotifications(){
    this.checkFinishCondition();
    if(!this.finishCondition){
      switch(this.notificationType){
        case 1:
          this.showDrawCardConditionBoolean = true; // show card draw option
          break;
        case 2:
          this.handleFightPlayer();
          break;
        case 3:
          this.showFightEnemyCardConditionBoolean = true; // show field enemy data
          // this.handleEnemyCardFight();
          break;
      }
    }
  }

  receiveCardActionFinished(){
    this.actionsDone++;
    this.showDrawCardConditionBoolean = false;
    this.rollCondition = false;
  }

  receiveRollFightEvent(data: boolean){
    this.rollCondition = data;
  }

  recieveFinishConditionChange(data: boolean){
    this.finishCondition = data;
    this.rollCondition = false;
    // this.showFightEnemyCardConditionBoolean = false;
}

  // drawCard(){
  //   if(this.notificationData > this.cardsDrawn){
  //     this.cardsDrawn += 1;
  //     this.handleDrawCard();
  //   }
  // }

  // handleDrawCard(){
  //   this.drawCardSubscription = this.playedGameService.drawRandomCard(this.requestStructure.game!.id).subscribe( (data: Card) => {
  //     this.showDrawCardConditionBoolean = false;
  //     this.cardDisplayCondition = true;
  //     if(data.cardType == CardType.ITEM_CARD){
  //       let c = data as ItemCard;
  //       this.cardAttributes.push(c.health);
  //       this.cardAttributes.push(c.strength);
  //       this.handleItemCard(c);
  //     }
  //     if(data.cardType == CardType.ENEMY_CARD){
  //       let c = data as EnemyCard;
  //       this.cardAttributes.push(c.health);
  //       this.cardAttributes.push(c.initialStrength);
  //       this.handleEnemyCard(c);
  //     }
  //   });
  // }

  // handleEnemyCard(data: EnemyCard){
  //   this.subscriptionToDelete.push(
  //     this.engineService.getCard(data.id).subscribe( (data: EngineCard) => {
  //       this.cardToDisplay = data;
  //       this.rollCondition = true; // show Roll Die Button
  //     })
  //   );
  // }

  // fightEnemy(){
  //   this.playerRoll = this.dieData.rollValue;
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: number) => {
  //       this.enemyRoll = data;
  //       this.handleFightAfterDraw();
  //     })
  //   )
  // }

  // handleItemCard(data: ItemCard){
  //   this.subscriptionToDelete.push(
  //     this.engineService.getCard(data.id).subscribe( (data: EngineCard) => {
  //       this.cardToDisplay = data;
  //       this.checkHandCards();
  //     })
  //   );
  // }

  // checkHandCards(){
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.getCardsFromPlayerHand(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: ItemCardList) => {
  //       if(data.itemCardList.length < 5){
  //         this.equipCondition = true;
  //       }
  //       else{
  //         this.handCondition = true;
  //         this.actionsDone++;
  //       }
  //     })
  //   )
  // }

  // equip(){
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.moveItemCardFromDeckToPlayerHand(
  //       this.requestStructure.game!.id, 
  //       this.cardToDisplay!.id, 
  //       this.requestStructure.player!.login).subscribe( () => {
  //         this.shared.sendEquipItemCardClickEvent();
  //         this.reset();
  //         // this.handleNotifications();
  //         this.actionsDone++;
  //       }
  //     )
  //   )
  // }

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

  // handleFightAfterDraw(){
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.handleFightWithEnemyCard(
  //       this.requestStructure.game!.id,
  //       this.requestStructure.player!.login,
  //       this.playerRoll,
  //       this.cardToDisplay.id,
  //       this.enemyRoll
  //     ).subscribe( (data: FightResult) => {
  //       this.fightResult = data;
  //       this.reset();
  //       this.fightResultCondition = true;
  //       if(!data.attackerWon){
  //         this.actionsDone++;
  //       }
  //       this.shared.sendUpdateStatisticsEvent();
  //     })
  //   )
  // }

  // getTrophy(){
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.moveCardToPlayerTrophies(
  //       this.requestStructure.game!.id,
  //       this.requestStructure.player!.login,
  //       this.cardToDisplay.id
  //     ).subscribe( () => {
  //       this.shared.sendUpdateStatisticsEvent();
  //       this.reset();
  //       this.actionsDone++;
  //     })
  //   )
  // }

  // handleEnemyCardFight(){
  //   this.checkField();
  // }

  // checkField(){
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.getPlayer(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: Player) => {
  //       if(data.character.field!.id == this.requestStructure.bossFieldId!){
  //         this.bossRoomFlag = true;
  //       }
  //       if(data.character.field!.id == this.requestStructure.bridgeFieldId!){
  //         this.bridgeFlag = true;
  //       }
  //       this.fetchEnemy();
  //     })
  //   )
  // }

  // fetchEnemy(){
  //   console.log(this.bridgeFlag);
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.getEnemiesToFightWith(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: EnemyCardList) => {
  //       let chosenEnemy!: EnemyCard;
  //       data.enemyCardList.forEach( (ec: EnemyCard) => {
  //         if(ec.id == this.notificationData){
  //           chosenEnemy = ec;
  //         }
  //       })
  //       if(chosenEnemy){
  //         this.cardDisplayCondition = true;
  //         this.cardAttributes.push(chosenEnemy.health);
  //         this.cardAttributes.push(chosenEnemy.initialStrength);
  //         this.handleEnemyCard(chosenEnemy);
  //       }
  //     })
  //   )
  // }

  // fightEnemyFromField(){
  //   this.playerRoll = this.dieData.rollValue;
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: number) => {
  //       this.enemyRoll = data;
  //       this.handleFightEnemyField();
  //     })
  //   )
  // }

  // handleFightEnemyField(){
  //   console.log(this.playerRoll);
  //   console.log(this.enemyRoll);
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.handleFightWithEnemyCard(
  //       this.requestStructure.game!.id,
  //       this.requestStructure.player!.login,
  //       this.playerRoll,
  //       this.cardToDisplay.id,
  //       this.enemyRoll
  //     ).subscribe( (data: FightResult) => {
  //       console.log(data);
  //       this.fightResult = data;
  //       this.reset();
  //       this.fightResultCondition = true;
  //       if(!this.bossRoomFlag && !this.fightResult.attackerWon){
  //         this.finishCondition = true;
  //       }
  //       this.shared.sendUpdateStatisticsEvent();
  //     })
  //   )
  // }

  // goToBoss(){
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.changeFieldPositionOfCharacter(
  //       this.requestStructure.game!.id,
  //       this.requestStructure.player!.login,
  //       this.requestStructure.bossFieldId!
  //     ).subscribe( () => {
  //       this.reset()
  //       this.finishCondition = true;
  //     })
  //   )
  // }

  // goToBridge(){
  //   this.subscriptionToDelete.push(
  //     this.playedGameService.changeFieldPositionOfCharacter(
  //       this.requestStructure.game!.id,
  //       this.requestStructure.player!.login,
  //       this.requestStructure.bridgeFieldId!
  //     ).subscribe( () => {
  //       this.reset()
  //       this.finishCondition = true;
  //     })
  //   )
  // }

  // stayOnBoss(){
  //   this.reset()
  //   this.finishCondition = true;
  // }

  handleBlockTurn(){
  //   TO DISCUSS
  }

  // rollDie(){
  //   this.rollDieSubscription = this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe((data: number) => {
  //     this.rollValue = data;
  //     this.fightEnemyCondition = true;
  //   });
  // }

  recieveDieData(data: { flag: boolean, value: number }){
    this.dieData = {fightEnemyCondition: data.flag, rollValue: data.value};
  }

  continue(){
    this.reset();
    this.actionsDone = 0;
    this.handleNotifications();
  }

  close(){
    this.reset();
    this.shared.sendNotificationCloseEvent();
  }

  reset(){
    this.showDrawCardConditionBoolean = false;
    this.cardDisplayCondition = false;
    this.handCondition = false; 
    this.equipCondition = false;
    this.rollCondition = false;
    // this.fightEnemyCondition = false;
    this.fightResultCondition = false;
    this.showFightEnemyCardConditionBoolean = false;
    // this.bridgeFlag = false;
    // this.bossRoomFlag = false;
    this.dieData = {fightEnemyCondition: false, rollValue: 0};
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
