import { Component, OnInit } from '@angular/core';
import { GameDataStructure } from 'src/app/interfaces/game-data-structure';
import { NotificationMessage } from 'src/app/interfaces/played-game/notification/notification-message';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit{
  private RequestStrucutre!: GameDataStructure;
  private ws!: WebSocket;
  private test!: NotificationMessage;
  
  constructor(private shared: SharedService){
    
  }

  ngOnInit(): void {
    this.RequestStrucutre = this.shared.getRequest();
    this.shared.initSocket(this.RequestStrucutre.game!.id);
    this.ws = this.shared.getSocket();
    this.shared.getSocketMessage().subscribe((data: any) => {
      console.log('data from message broadcast subject');
      this.test = this.shared.parseNotificationMessage(data);
      console.log(this.test);
    });
  }
}
