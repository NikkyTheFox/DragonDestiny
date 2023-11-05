import { Component, OnInit } from '@angular/core';
import { SharedService } from './services/shared.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  title = 'Dragon Destiny'

  // constructor(private shared: SharedService){

  // }

  // ngOnInit(): void {
  //     this.shared.getSocketMessage().subscribe( (data: any) => {
  //       console.log(data);
  //     });
  // }
  
}
