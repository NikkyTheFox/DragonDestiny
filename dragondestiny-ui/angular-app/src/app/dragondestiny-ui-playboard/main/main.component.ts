import { Component, OnInit } from '@angular/core';
import { GameDataStructure } from 'src/app/interfaces/game-data-structure';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{
  private RequestStrucutre!: GameDataStructure;
  
  constructor(private shared: SharedService){
    
  }

  ngOnInit(): void {
    this.RequestStrucutre = this.shared.getRequest();
    this.shared.initSocket(this.RequestStrucutre.game!.id);
  }
}
