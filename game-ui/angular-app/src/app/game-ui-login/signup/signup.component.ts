import { Component } from '@angular/core';
import {GameUserService} from "../../services/game-user.service";
import {GameDataService} from "../../services/game-data.service";
import {Router} from "@angular/router";
import {GameUserRegistration} from "../../interfaces/game-user/game-user-registration";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  login: string
  name: string;
  password: string;

  constructor(private userService: GameUserService, private dataService: GameDataService, private router: Router) {
    this.login="";
    this.name="";
    this.password="";
  }

  signup(){
    const user: GameUserRegistration = {
      login: this.login,
      name: this.name,
      password: this.password
    }

    this.userService.registerUser(user).subscribe((data: any)=>{
        this.dataService.loginData = data;
        this.dataService.loginFlag = true;
        this.router.navigate(["/dashboard"]);
      },
      (error: any) =>{ // Handle 404
        //window.alert("User not found");
      });
  }
}
