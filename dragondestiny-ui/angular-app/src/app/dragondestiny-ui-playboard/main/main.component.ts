import {Component, OnInit } from '@angular/core';
import { GameDataService } from '../../services/game-data.service';
import { SharedService } from "../../services/shared.service";
import {GamePlayerRequest} from "../../interfaces/game-player-request";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{

  constructor(private dataService: GameDataService, private shared: SharedService){
  }

  ngOnInit(){
    this.shared.setRequestByID(this.dataService.getGame(), this.dataService.getPlayerLogin());
  }
}
