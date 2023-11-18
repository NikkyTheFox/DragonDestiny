import { PlayedGameService } from './../../../../../services/played-game/played-game-service';
import { Component, EventEmitter, OnDestroy, OnInit, Output } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { GameDataStructure } from 'src/app/interfaces/game-data-structure';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-notification-die',
  templateUrl: './notification-die.component.html',
  styleUrls: ['./notification-die.component.css']
})

export class NotificationDieComponent implements OnInit, OnDestroy {
  @Output() outputCondition = new EventEmitter<{flag: boolean, value: number}>();

  rollDieSubscription!: Subscription;
  rollValue: number = 0;
  requestStructure!: GameDataStructure;

  constructor(private playedGameService: PlayedGameService, private shared: SharedService){

  }

  ngOnInit(): void {
      this.requestStructure = this.shared.getRequest();
  }

  rollDie(){
    this.rollDieSubscription = this.playedGameService.rollDice(this.requestStructure.game!.id, this.requestStructure.player!.login).subscribe((data: number) => {
      this.rollValue = data;
      this.outputCondition.emit(
        {
          flag: true, 
          value: data
        }
      );
    });
  }

  ngOnDestroy(): void {
    this.rollDieSubscription?.unsubscribe;
  }
}
