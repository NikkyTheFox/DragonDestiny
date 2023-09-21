import { Component } from '@angular/core';
import { GameDataService } from "../../services/game-data.service";

@Component({
  selector: 'app-header-right',
  templateUrl: './header-right.component.html',
  styleUrls: ['./header-right.component.css']
})
export class HeaderRightComponent {

  constructor(protected dataService: GameDataService) {
  }
}
