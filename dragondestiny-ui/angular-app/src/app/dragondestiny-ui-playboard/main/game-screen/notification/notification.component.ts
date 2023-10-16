import {Component, Input, OnInit} from '@angular/core';
import {PlayedGameService} from "../../../../services/played-game/played-game-service";
import {SharedService} from "../../../../services/shared.service";
import {GameDataStructure} from "../../../../interfaces/game-data-structure";
import {FightResult} from "../../../../interfaces/played-game/fight-result/fight-result";
import {Card} from "../../../../interfaces/played-game/card/card/card";

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit{
  requestStructure!: GameDataStructure;
  @Input() notificationType!: number;
  @Input() notificationData!: any;
  rollValue: number = 0;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService) {
  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.handleNotifications();
  }

  handleNotifications(){
    switch(this.notificationType){
      case 1:
        this.handleDrawCard();
        break;
      case 2:
        this.handleFightPlayer();
        break;
      case 3:
        this.handleFightEnemy();
        break;
    }
  }

  handleDrawCard(){
    this.playedGameService.drawRandomCard(this.requestStructure.game!.id).subscribe( (data: Card) => {
    // Handle card draw data to display in html file
    });
  }

  handleFightPlayer(){
    // ROLL A DIE
    let roll = 6;
    this.playedGameService.handleFightWithPlayer(
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
    this.playedGameService.handleFightWithEnemyCard(
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
    this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe((data: number) => {
      this.rollValue = data;
    });
  }

  close(){
    this.shared.sendNotificationCloseEvent();
  }
}
