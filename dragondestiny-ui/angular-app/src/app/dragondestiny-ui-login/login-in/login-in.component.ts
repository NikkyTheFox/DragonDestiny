import { Component } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { UserLogin } from '../../interfaces/user/user/user-login';
import { GameDataService } from '../../services/game-data.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-in',
  templateUrl: './login-in.component.html',
  styleUrls: ['./login-in.component.css']
})
export class LoginInComponent {
  name!: string;
  password!: string;

  constructor(private userService: UserService, private dataService: GameDataService, private router: Router) {
  }

  login(){
    const user: UserLogin = {
      login: this.name,
      password: this.password
    }
    this.userService.getUserByLoginPassword(user).subscribe((data: any)=>{
      this.dataService.loginData = data;
      this.dataService.loginFlag = true;
      this.router.navigate(['/dashboard']);
    },
      (error: any) =>{ // Handle 404 and others
        window.alert('User not found');
      });
  }

}
