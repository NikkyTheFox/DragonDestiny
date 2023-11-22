import { Injectable } from '@angular/core';
import { User } from '../interfaces/user/user/user';
import { Field } from '../interfaces/played-game/field/field';

@Injectable({
  providedIn: 'root'
})
export class GameDataService{
  loginData: User = {
    login: '',
    name: '',
    playedGames: []
  }
  loginFlag: boolean = false;
  chosenGame: string = '';
  possibleFields: Field[] = [];

  isAuthorized(){
    return this.loginFlag;
  }

  getPlayerLogin(){
    return this.loginData.login;
  }

  getGame(){
    return this.chosenGame;
  }
}
