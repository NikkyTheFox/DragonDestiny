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
  rollValue: number = 0;
  conditionBoolean: boolean = false;
  cardToDisplay!: EngineCard;
  cardAttributes: number[] = [];

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

  handleNotifications(){
    switch(this.notificationType){
      case 1:
        // this.handleDrawCard();
        this.conditionBoolean = true;
        break;
      case 2:
        this.handleFightPlayer();
        break;
      case 3:
        this.handleFightEnemy();
        break;
    }
  }

  drawCard(){
    if(this.notificationData > 0){
      this.notificationData--;
      this.handleDrawCard()
    }
  }

  handleDrawCard(){
    this.drawCardSubscription = this.playedGameService.drawRandomCard(this.requestStructure.game!.id).subscribe( (data: Card) => {
      console.log(data);
      if(data.cardType === CardType.ITEM_CARD){
        let c = data as ItemCard;
        this.cardAttributes.push(c.health);
        this.cardAttributes.push(c.strength);
        this.handleItemCard(c);
      }
      else{
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
        console.log(this.cardToDisplay);
      })
    );
  }

  handleItemCard(data: ItemCard){
    this.subscriptionToDelete.push(
      this.engineService.getCard(data.id).subscribe( (data: EngineCard) => {
        this.cardToDisplay = data;
        console.log(this.cardToDisplay);
      })
    );
  }

  handleFightPlayer(){
    // ROLL A DIE
    let roll = 6;
    this.playerFightSubscription = this.playedGameService.handleFightWithPlayer(
      this.requestStructure.game!.id,
      this.requestStructure.player!.login,
      roll,
      this.notificationData
    ).subscribe( (data: FightResult)=> {
    // Handle fightResult data to be displayed in html file.
    });
  }

  handleFightEnemy(){
    // ROLL A DIE
    let roll = 6;
    let rollEnemy = 1;
    this.enemyFightSubscription = this.playedGameService.handleFightWithEnemyCard(
      this.requestStructure.game!.id,
      this.requestStructure.player!.login,
      roll,
      this.notificationData,
      rollEnemy
    ).subscribe( (data: FightResult) => {
    // Handle fightResult data to be displayed in html file.
    });
  }

  handleBlockTurn(){
  //   TO DISCUSS
  }

  rollDie(){
    this.rollDieSubscription = this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe((data: number) => {
      this.rollValue = data;
    });
  }

  close(){
    this.shared.sendNotificationCloseEvent();
  }

  ngOnDestroy(): void {
    this.rollDieSubscription?.unsubscribe();
    this.enemyFightSubscription?.unsubscribe();
    this.playerFightSubscription?.unsubscribe();
    this.drawCardSubscription?.unsubscribe();
  }
}
