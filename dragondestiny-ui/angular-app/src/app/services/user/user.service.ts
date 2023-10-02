import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { User } from '../../interfaces/user/user/user';
import { UserRegistered } from '../../interfaces/user/user/user-registered';
import { UserLogin } from '../../interfaces/user/user/user-login';
import { UserList } from '../../interfaces/user/user/user-list';
import { GameList } from '../../interfaces/user/game/game-list';

@Injectable({
  providedIn: 'root'
})
export class UserService{

  constructor(private  http: HttpClient){

  }

  getUsers():Observable<UserList>{
    return this.http.get<UserList>(`${environment.apiUrl}/users`);
  }

  getUserByLogin(login: string): Observable<User>{
    return this.http.get<User>(`${environment.apiUrl}/users/${login}`);
  }

  getUserByLoginPassword(user: UserLogin): Observable<User>{
    return this.http.put<User>(`${environment.apiUrl}/users/login`, user);
  }

  createUser(user: UserRegistered): Observable<User>{
    return this.http.put<User>(`${environment.apiUrl}/users/register`, user);
  }

  getUsersGames(login: string): Observable<GameList>{
    return this.http.get<GameList>(`${environment.apiUrl}/users/${login}/games`);
  }

  updateUser(userLogin: string, updatedUser: UserRegistered): Observable<User>{
    return this.http.put<User>(`${environment.apiUrl}/users/${userLogin}/edit`, updatedUser);
  }

  addGameToUser(login: string, gameId: string): Observable<User>{
    return this.http.put<User>(`${environment.apiUrl}/users/${login}/addGame/${gameId}`, null);
  }

  deleteUser(login: string): Observable<boolean>{
    return this.http.delete<boolean>(`${environment.apiUrl}/users/${login}`);
  }

  addGame(gameId: string): Observable<boolean>{
    return this.http.put<boolean>(`${environment.apiUrl}/users/games/${gameId}`, null);
  }

  getGames(): Observable<GameList>{
    return this.http.get<GameList>(`${environment.apiUrl}/users/games`);
  }
}
