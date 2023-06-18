import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {GameUserShort} from "../interfaces/game-user/game-user-short";
import {GameUserRegistration} from "../interfaces/game-user/game-user-registration";
import {GameUserLoginPassOnly} from "../interfaces/game-user/game-user-login-pass-only";
import {GameUserPlayedGame} from "../interfaces/game-user/game-user-played-game";
@Injectable({
  providedIn: 'root'
})
export class GameUserService {

  constructor(private  http: HttpClient) { }

  getUsers():Observable<GameUserShort[]>{
    return this.http.get<GameUserShort[]>(`${environment.apiUrl}/users`);
  }

  getUserByLogin(login: string):Observable<GameUserShort>{
    return this.http.get<GameUserShort>(`${environment.apiUrl}/users/${login}`);
  }

  getUserByLoginPasswordObject(user: GameUserLoginPassOnly):Observable<GameUserShort>{
    return this.http.put<GameUserShort>(`${environment.apiUrl}/users/login`, user);
  }

  registerUser(user: GameUserRegistration){
    return this.http.put<any>(`${environment.apiUrl}/users/register`, user);
  }

  getUserPlayedGames(login: string):Observable<GameUserPlayedGame[]>{
    return this.http.get<GameUserPlayedGame[]>(`${environment.apiUrl}/users/${login}/games`);
  }

  editUser(update: GameUserRegistration){
    return this.http.put<any>(`${environment.apiUrl}/users/edit`, update);
  }

  addUserPlayedGame(login: string, gameId: string){
    return this.http.put(`${environment.apiUrl}/users/${login}/addGame/${gameId}`, null);
  }

  deleteUser(login: string){
    return this.http.delete(`${environment.apiUrl}/users/${login}`);
  }
}
