import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private diceRoll = new Subject<any>();
  private moveCharacter = new Subject<any>();
  private drawCard = new Subject<any>();
  private drawEnemyCard = new Subject<any>();
  private equipItemCard = new Subject<any>();
  private fightPlayer = new Subject<any>();
  private fightEnemyCard = new Subject<any>();
  private fightEnemyOnField = new Subject<any>();

  sendDiceRollClickEvent(){
    // @ts-ignore
    this.diceRoll.next();
  }

  getDiceRollClickEvent(){
    return this.diceRoll.asObservable();
  }

  sendMoveCharacterClickEvent(){
    // @ts-ignore
    this.moveCharacter.next();
  }

  getMoveCharacterClickEvent(){
    return this.moveCharacter.asObservable();
  }

  sendDrawCardClickEvent(){
    // @ts-ignore
    this.drawCard.next();
  }

  getDrawCardClickEvent(){
    return this.drawCard.asObservable();
  }

  sendDrawEnemyCardClickEvent(){
    // @ts-ignore
    this.drawEnemyCard.next();
  }

  getDrawEnemyCardClickEvent(){
    return this.drawEnemyCard.asObservable();
  }

  sendEquipItemCardClickEvent(){
    // @ts-ignore
    this.equipItemCard.next();
  }

  getEquipItemCardClickEvent(){
    return this.equipItemCard.asObservable();
  }

  sendFightPlayerClickEvent(){
    // @ts-ignore
    this.fightPlayer.next();
  }

  getFightPlayerClickEvent(){
    return this.fightEnemyCard.asObservable();
  }

  sendFightEnemyCardClickEvent(){
    // @ts-ignore
    this.fightEnemyCard.next();
  }

  getFightEnemyCardClickEvent(){
    return this.fightEnemyCard.asObservable();
  }

  sendFightEnemyOnFieldClickEvent(){
    // @ts-ignore
    this.fightEnemyOnField.next();
  }

  getFightEnemyOnFieldClickEvent(){
    return this.fightEnemyOnField.asObservable();
  }
  constructor() { }
}
