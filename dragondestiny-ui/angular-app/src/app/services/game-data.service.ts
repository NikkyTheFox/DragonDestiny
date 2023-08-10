import { Injectable } from '@angular/core';
import {GameUserShort} from "../interfaces/game-user/game-user-short";
import {Field} from "../interfaces/game-engine/field";

@Injectable({
  providedIn: 'root'
})
export class GameDataService {

  loginData: GameUserShort = {
    login: "",
    name: "",
    playedGames: []
  }

  loginFlag: boolean = false;

  chosenGame: string = "";

  possibleFields: Field[];
  constructor() {
    this.possibleFields = [];
  }
}
