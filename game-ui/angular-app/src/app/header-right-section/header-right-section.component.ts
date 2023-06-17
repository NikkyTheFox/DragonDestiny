import { Component } from '@angular/core';
import {GameDataService} from "../services/game-data.service";

@Component({
  selector: 'app-header-right-section',
  templateUrl: './header-right-section.component.html',
  styleUrls: ['./header-right-section.component.css']
})
export class HeaderRightSectionComponent {

  constructor(protected dataService: GameDataService) {
  }
}
