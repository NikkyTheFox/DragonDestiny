import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayedGameService } from '../../../../../services/played-game/played-game-service';
import { GameDataService } from '../../../../../services/game-data.service';
import { SharedService } from '../../../../../services/shared.service';
import { GameDataStructure } from '../../../../../interfaces/game-data-structure';
import { Subscription } from 'rxjs';
import { Round } from 'src/app/interfaces/played-game/round/round';
import { RoundState } from 'src/app/interfaces/played-game/round/round-state';
import { FieldList } from 'src/app/interfaces/played-game/field/field-list';

@Component({
  selector: 'app-game-controls-dice',
  templateUrl: './game-controls-dice.component.html',
  styleUrls: ['./game-controls-dice.component.css']
})
export class GameControlsDiceComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  rollValue: number = 0;
  disableRoll: boolean = false;
  bossRoomFlag: boolean = false;
  bridgeFlag: boolean = false;

  constructor(private playedGameService: PlayedGameService, private dataService: GameDataService, private shared: SharedService){

  }

  ngOnInit(){
    this.requestStructure = this.shared.getRequest();
    this.toDeleteSubscription.push(
      this.shared.getEndTurnEvent().subscribe( () => {
        this.fetchRound();
        this.disableRoll = false;
      })
    );
    this.fetchRound();
    // this.fetchFieldData();
  }

  // fetchFieldData(){
  //   this.fetchFieldDataSubscription = this.playedGameService.getPlayer(
  //     this.requestStructure.game!.id, 
  //     this.requestStructure.player!.login).subscribe( (data: Player) => {
  //       if(data.character.field!.id == this.requestStructure.bossFieldId!){
  //         this.bossRoomFlag = true;
  //       }
  //       if(data.character.field!.id == this.requestStructure.bridgeFieldId!){
  //         this.bridgeFlag = true;
  //       }
  //     }
  //   );
  // }

  fetchRound(){
    this.toDeleteSubscription.push(
      this.playedGameService.getActiveRound(this.requestStructure.game!.id).subscribe( (data: Round) => {
        // Disables rollDie button if roundState does not allow rolling for move, If so, display roll value from Round
        this.disableRoll = !(data.roundState == RoundState.WAITING_FOR_MOVE_ROLL);
        if(this.disableRoll){
          this.rollValue = data.playerMoveRoll;
        }
      })
    );
  }

  rollDice(){
    this.toDeleteSubscription.push(
      this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe((data: number) => {
        this.rollValue = data;
        this.disableRoll = true;
        this.checkPositions();
      })
    );
  }

  checkPositions(){
    this.toDeleteSubscription.push(
      this.playedGameService.checkPossibleNewPositions(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe((data: FieldList) => {
        this.dataService.possibleFields = data.fieldList;
        this.shared.sendDiceRollClickEvent();
      })
    );
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
