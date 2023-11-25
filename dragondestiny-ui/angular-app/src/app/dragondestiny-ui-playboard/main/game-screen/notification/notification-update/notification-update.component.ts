import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-notification-update',
  templateUrl: './notification-update.component.html',
  styleUrls: ['./notification-update.component.css']
})
export class NotificationUpdateComponent {
  @Input() notificationData!: string;
}
