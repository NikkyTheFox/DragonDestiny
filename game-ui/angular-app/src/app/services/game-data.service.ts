import { Injectable } from '@angular/core';
import {GameUserShort} from "../interfaces/game-user/game-user-short";

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
  constructor() { }
}
