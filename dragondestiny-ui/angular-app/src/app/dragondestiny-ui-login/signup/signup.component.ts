import { Component, OnDestroy } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { GameDataService } from '../../services/game-data.service';
import { Router } from '@angular/router';
import { UserRegistered } from '../../interfaces/user/user/user-registered';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnDestroy{
  createUserSubscripiton!: Subscription;

  login!: string
  name!: string;
  password!: string;

  constructor(private userService: UserService, private dataService: GameDataService, private router: Router){

  }

  signup(){
    const user: UserRegistered = {
      login: this.login,
      name: this.name,
      password: this.password
    }
    this.createUserSubscripiton = this.userService.createUser(user).subscribe((data: any) => {
        this.dataService.loginData = data;
        this.dataService.loginFlag = true;
        this.router.navigate(['/dashboard']);
      },
      (error: any) =>{ // Handle 404 and other
        window.alert('Could not create user account');
      });
  }

  ngOnDestroy(): void {
      this.createUserSubscripiton?.unsubscribe();
  }
}
