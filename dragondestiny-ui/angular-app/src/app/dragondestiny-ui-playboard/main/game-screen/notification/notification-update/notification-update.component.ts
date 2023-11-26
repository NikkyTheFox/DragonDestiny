import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-notification-update',
  templateUrl: './notification-update.component.html',
  styleUrls: ['./notification-update.component.css']
})
export class NotificationUpdateComponent implements OnInit{
  @Input() notificationData!: any;
  @Input() notificationData2!: any;
  @Output() finishConditionChange = new EventEmitter<boolean>();
  showFlag: boolean = false;

  ngOnInit(): void {
    this.showFlag = true;
  }

  finishAction(){
    this.showFlag = false;
    this.finishConditionChange.emit(true);
  }
}
