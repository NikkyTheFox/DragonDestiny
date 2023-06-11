import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import {Observable} from "rxjs";
import {Game} from "./game";
import {Board} from "./board";

@Injectable({
  providedIn: 'root'
})
export class GameServiceService {

  constructor(private http: HttpClient) { }

  getGames(){
    return this.http.get(`${environment.apiUrl}/games`);
  }

  getGame(id: number): Observable<Game>{
    return this.http.get<Game>(`${environment.apiUrl}/games/${id}`)
  }

  getGamesBoard(id: number): Observable<Board>{
    return this.http.get<Board>(`${environment.apiUrl}/games/${id}/boards`)
  }

}
