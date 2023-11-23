import { Component, OnInit } from '@angular/core';
import { WebsocketService } from './websocket.service';

@Component({
  selector: 'app-root',
  template: `
    <button (click)="sendMessage()">Send Message</button>
    JELLO
    <ul>
      <li *ngFor="let item of receivedMessages">{{ item }}</li>
    </ul>
  `
})
export class AppComponent implements OnInit {
  receivedMessages: string[] = ['ONE'];

  constructor(private websocketService: WebsocketService) {}

  ngOnInit(): void {
    this.websocketService.connect();
    this.receivedMessages.push('TWO');
    this.websocketService.game.subscribe((message: string) => {
      this.receivedMessages.push(message);
    });
  }

  sendMessage(): void {
    const message = 'Hello, WebSocket!';
    this.websocketService.sendMessage(message);
  }
}
