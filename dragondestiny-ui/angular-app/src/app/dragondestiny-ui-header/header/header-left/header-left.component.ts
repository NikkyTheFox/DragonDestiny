import { Component, DoCheck, OnDestroy, OnInit } from '@angular/core';
import { GameDataService } from '../../../services/game-data.service';
import { SharedService } from 'src/app/services/shared.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header-left',
  templateUrl: './header-left.component.html',
  styleUrls: ['./header-left.component.css']
})
export class HeaderLeftComponent implements OnInit, OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  playerLogin!: string;

  constructor(private dataService: GameDataService, private shared: SharedService){

  }

  ngOnInit(): void {
    this.toDeleteSubscription.push(
      this.shared.getPlayerLoginEvent().subscribe( () => {
        this.playerLogin = this.dataService.loginData.login;
      })
    );
  }
  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }
}
