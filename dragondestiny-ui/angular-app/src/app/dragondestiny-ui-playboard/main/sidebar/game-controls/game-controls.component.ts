import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../services/played-game/played-game-service';
import { SharedService } from '../../../../services/shared.service';
import { GameDataStructure } from '../../../../interfaces/game-data-structure';
import { Round } from '../../../../interfaces/played-game/round/round';
import { Subscription } from 'rxjs';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';
import { UpdateEnum } from 'src/app/interfaces/played-game/notification/update-enum';

@Component({
  selector: 'app-game-controls',
  templateUrl: './game-controls.component.html',
  styleUrls: ['./game-controls.component.css']
})
export class GameControlsComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  round!: Round;
  isActive: boolean = false;
  activePlayerLogin!: string;
  activePlayerCharacter!: number;
  messageData!: NotificationMessage;
  playerAttackedFlag: boolean = false;
  cardStolenFlag: boolean = false;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService){

  }
  ngOnInit() {
    this.requestStructure = this.shared.getRequest();
    this.fetchRound();
    this.toDeleteSubscription.push(
      this.shared.getSocketMessage().subscribe( (data: any) => {
        this.messageData = this.shared.parseNotificationMessage(data);
        if(this.messageData.notificationOption === NotificationEnum.NEXT_ROUND){
          this.fetchRound();
        }
        console.log('WebSocketMessage in Controls-Options:')
        console.log(this.messageData);
        this.playerAttackedFlag = this.messageData.notificationOption == NotificationEnum.PLAYER_ATTACKED;
        if(this.playerAttackedFlag){
          // needs to be handled after round data is fetched
          this.fetchRound()
        }
        if(this.messageData.notificationOption == NotificationEnum.PLAYER_WON_GAME){
          this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_WON_GAME, this.messageData.name, null, null, null);
        }
        else if(this.messageData.notificationOption == NotificationEnum.PLAYER_DIED){
          this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_DIED, this.messageData.name, null, null, null);
        }
        else if(this.messageData.notificationOption == NotificationEnum.PLAYER_BLOCKED){
          this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_BLOCKED, this.messageData.name, null, null, this.messageData.number);
        }
        else if(this.messageData.notificationOption == NotificationEnum.PLAYER_FIGHT){
          this.processPlayerFight();
        }
        else if(this.messageData.notificationOption == NotificationEnum.PLAYER_GOT_ITEM){
          this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_GOT_ITEM, this.messageData.name, null, this.messageData.number, null);
        }
        this.cardStolenFlag = this.messageData.notificationOption == NotificationEnum.CARD_STOLEN;
        if(this.cardStolenFlag){
          // needs to be handled after round data is fetched
          this.fetchRound()
        }
      })
    );
  }

  fetchRound(){
    this.toDeleteSubscription.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        this.round = data;
        this.activePlayerLogin = data.activePlayer.login;
        this.activePlayerCharacter = data.activePlayer.character.id;
        this.isActive = this.activePlayerLogin === this.requestStructure.player!.login;
        if(this.playerAttackedFlag){
          this.processPlayerAttacked();
        }
        else if(this.cardStolenFlag){
          this.processCardStolen();
        }
      })
    );
  }

  handleDefenderPlayer(attackerPlayerLogin: string){
    this.shared.sendNotifyDefenderPlayerEvent(attackerPlayerLogin);
  }

  processPlayerFight(){
    let winnerLogin: string | null = null;
    let loserLogin: string | null = null;
    let cardId: number | null = null;
    if(this.messageData.bool){ // info about winner player
      winnerLogin = this.messageData.name
      if(this.messageData.number){ // check whether the fight was with enemy card
        cardId = this.messageData.number;
      }
    }
    if(!this.messageData.bool){ // info about loser player
      loserLogin = this.messageData.name;
      if(this.messageData.number){ // check whether the fight was with enemy card
        cardId = this.messageData.number;
      }
    }
    this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_FIGHT, winnerLogin, loserLogin, cardId, null);
  }

  processPlayerAttacked(){
    // If logged in player is attacked -> proceed to the notification-defend
    if(this.round.enemyPlayerFought.login == this.requestStructure.player!.login){ 
      this.handleDefenderPlayer(this.round.activePlayer.login)
    }
    // notify OTHER players (notification-update)
    else {            
      this.shared.sendUpdateGameEvent(UpdateEnum.PLAYER_ATTACKED, this.round.activePlayer.login, this.round.enemyPlayerFought.login, null, null);
    }
  }

  processCardStolen(){
    if(this.round.enemyPlayerFought.login == this.requestStructure.player!.login || this.round.activePlayer.login == this.requestStructure.player!.login){
      // Reload HAND cards for fight participants
      this.shared.sendRefreshHandCardsEvent();
    }
    else{
      let winnerLogin: string | null = null;
      let loserLogin: string | null = null;
      if(this.round.activePlayer.character.strength + this.round.playerFightRoll > this.round.enemyPlayerFought.character.strength + this.round.playerFightRoll){
        winnerLogin = this.round.activePlayer.login;
        loserLogin = this.round.enemyPlayerFought.login;
      }
      else{
        winnerLogin = this.round.enemyPlayerFought.login;
        loserLogin = this.round.activePlayer.login;
      }
      // WAIT FOR BACKEND CARD_ID IMPLEMENTATION
      this.shared.sendUpdateGameEvent(UpdateEnum.CARD_STOLEN, winnerLogin, loserLogin, this.round.itemCardStolen.id, null);
    }
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}