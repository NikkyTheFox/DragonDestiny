import {Component} from '@angular/core';
import {UserService} from "../../services/user.service";
import {GameUserLoginPassOnly} from "../../interfaces/game-user/game-user-login-pass-only";
import {GameDataService} from "../../services/game-data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login-in',
  templateUrl: './login-in.component.html',
  styleUrls: ['./login-in.component.css']
})
export class LoginInComponent {
  name: string;
  password: string;

  constructor(private userService: UserService, private dataService: GameDataService, private router: Router) {
    this.name="";
    this.password="";
  }

  login(){
    const user: GameUserLoginPassOnly = {
      login: this.name,
      password: this.password
    }
    this.userService.getUserByLoginPasswordObject(user).subscribe((data: any)=>{
      this.dataService.loginData = data;
      this.dataService.loginFlag = true;
      this.router.navigate(["/dashboard"]);
    },
      (error: any) =>{ // Handle 404
        //window.alert("User not found");
      });
  }

}
