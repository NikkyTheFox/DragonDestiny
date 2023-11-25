import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { GameDataStructure } from 'src/app/interfaces/game-data-structure';
import { Character } from 'src/app/interfaces/game-engine/character/character';
import { PlayedGameCharacter } from 'src/app/interfaces/played-game/character/character';
import { FightResult } from 'src/app/interfaces/played-game/fight-result/fight-result';
import { NotificationEnum } from 'src/app/interfaces/played-game/notification/notification-enum';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { Player } from 'src/app/interfaces/played-game/player/player';
import { PlayerList } from 'src/app/interfaces/played-game/player/player-list';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { GameEngineService } from 'src/app/services/game-engine/game-engine.service';
import { PlayedGameService } from 'src/app/services/played-game/played-game-service';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-notification-attack',
  templateUrl: './notification-attack.component.html',
  styleUrls: ['./notification-attack.component.css']
})
export class NotificationAttackComponent implements OnInit, OnDestroy{
  @Input() notificationData!: any;
  @Input() gameContinueFlag!: boolean;
  @Input() dieData!: {fightEnemyCondition: boolean, rollValue: number}
  @Input() finishCondition!: boolean;
  @Input() showAttackingPlayerPOV!: boolean;
  @Output() finishConditionChange = new EventEmitter<boolean>();
  @Output() characterFightCondition = new EventEmitter<boolean>();

  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  fightResult!: FightResult;
  playerToDisplay!: Player;
  characterToDisplay!: Character;
  characterAttributes: number[] = [];
  fightResultCondition: boolean = false;
  characterDisplayCondition: boolean = false;
  fightPlayerCondition: boolean = false;
  round!: Round;
  playerRoll: number = 0;
  enemyRoll: number = 0;

  messageData!: NotificationMessage;

  constructor(private engineServie: GameEngineService, private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(): void {
    this.requestStructure = this.shared.getRequest();
    this.toDeleteSubscription.push(
      this.shared.getSocketMessage().subscribe( (data: any) => {
        this.messageData = this.shared.parseNotificationMessage(data);
        if(this.messageData.notificationOption === NotificationEnum.DICE_ROLLED){
          this.handleFight();
        };
      })
    );
    if(!this.gameContinueFlag){
      this.fetchEnemy();
    }
  }

  fetchEnemy(){
    this.toDeleteSubscription.push(
      this.playedGameService.getPlayersToFightWith(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe( (data: PlayerList) => {
        console.log('here1');
        this.characterDisplayCondition = true;
        this.playerToDisplay = data.playerList[0];
        this.characterAttributes.push(data.playerList[0].character.health);
        this.characterAttributes.push(data.playerList[0].character.strength);
        this.handleCharacter(data.playerList[0].character);
      })
    );
  }

  handleCharacter(data: PlayedGameCharacter){
    this.toDeleteSubscription.push(
      this.engineServie.getCharacter(data.id).subscribe( (data: Character) => {
        this.characterToDisplay = data;
        this.characterFightCondition.emit(true);
      })
    );
  }

  handleFight(){
    this.toDeleteSubscription.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        this.fightPlayerCondition = data.enemyFightRoll != null;
        this.round = data;
      })
    );
  }

  fightPlayer(){
    this.toDeleteSubscription.push(
      this.playedGameService.handleFightWithPlayer(this.requestStructure.game!.id, this.round.activePlayer.login, this.round.enemyPlayerFought.login).subscribe( (data: FightResult) => {
        this.fightResult = data;
        this.fightResultCondition = true;
      })
    );
  }

  stealCard(){
    // this.toDeleteSubscription.push(
    //   this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
    //     consol
    //   })
    // );
  }

  reset(){
    this.fightResultCondition = false;
    this.showAttackingPlayerPOV = false;
    this.dieData = {fightEnemyCondition: false, rollValue: 0};
    this.characterAttributes = [];
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s.unsubscribe();
    });
  }
}
