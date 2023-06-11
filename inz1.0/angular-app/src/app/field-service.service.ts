import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
@Injectable({
  providedIn: 'root'
})
export class FieldServiceService {

  constructor(private http: HttpClient) { }

  getFields(){
    return this.http.get(`${environment.apiUrl}/users`);
  }
}
