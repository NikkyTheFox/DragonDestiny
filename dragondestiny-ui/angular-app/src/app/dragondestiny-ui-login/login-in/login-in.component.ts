import { Component, OnDestroy } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { UserLogin } from '../../interfaces/user/user/user-login';
import { GameDataService } from '../../services/game-data.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { SharedService } from 'src/app/services/shared.service';

@Component({
  selector: 'app-login-in',
  templateUrl: './login-in.component.html',
  styleUrls: ['./login-in.component.css']
})
export class LoginInComponent implements OnDestroy{
  toDeleteSubscription: Subscription[] = [];
  name!: string;
  password!: string;

  constructor(private userService: UserService, private dataService: GameDataService, private router: Router, private shared: SharedService){

  }

  login(){
    const user: UserLogin = {
      login: this.name,
      password: this.password
    }
    this.toDeleteSubscription.push(
      this.userService.getUserByLoginPassword(user).subscribe((data: any) => {
        this.dataService.loginData = data;
        this.dataService.loginFlag = true;
        this.shared.sendPlayerLoginEvent();
        this.router.navigate(['/dashboard']);
      },
      (error: any) =>{
        window.alert('User not found');
      })
    );
  }

  ngOnDestroy(): void {
    this.toDeleteSubscription.forEach( (s: Subscription) => {
      s?.unsubscribe();
    });
  }

}
