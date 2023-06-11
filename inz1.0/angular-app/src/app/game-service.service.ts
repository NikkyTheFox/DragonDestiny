import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import {Observable} from "rxjs";
import {Game} from "./game";

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

  getGamesBoard(id: number){
    return this.http.get(`${environment.apiUrl}/games/${id}/boards`)
  }

}
