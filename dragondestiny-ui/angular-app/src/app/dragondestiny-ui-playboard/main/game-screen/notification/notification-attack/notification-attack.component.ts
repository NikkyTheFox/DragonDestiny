import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { GameDataStructure } from 'src/app/interfaces/game-data-structure';
import { Character } from 'src/app/interfaces/game-engine/character/character';
import { FightResult } from 'src/app/interfaces/played-game/fight-result/fight-result';

@Component({
  selector: 'app-notification-attack',
  templateUrl: './notification-attack.component.html',
  styleUrls: ['./notification-attack.component.css']
})
export class NotificationAttackComponent {
  @Input() notificationData!: any;
  @Input() dieData!: {fightEnemyCondition: boolean, rollValue: number}
  @Input() finishCondition!: boolean;
  @Input() showFightEnemyCardConditionBoolean!: boolean;
  @Output() finishConditionChange = new EventEmitter<boolean>();
  @Output() cardFightCondition = new EventEmitter<boolean>();

  toDeleteSubscription: Subscription[] = [];
  requestStructure!: GameDataStructure;
  fightResult!: FightResult;
  characterToDisplay!: Character;
  characterAttributesToDisplay: number[] = [];
}
