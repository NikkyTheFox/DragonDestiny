import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SignupComponent} from "./game-ui-login/signup/signup.component";
import {LoginInComponent} from "./game-ui-login/login-in/login-in.component";
import {MainComponent} from "./game-ui-playboard/main/main.component";
import {GameUiDashboardComponent} from "./game-ui-dashboard/game-ui-dashboard/game-ui-dashboard.component";
import {PrepareGameComponent} from "./game-ui-prepare-game/prepare-game/prepare-game.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  { path: 'login', component: LoginInComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'main', component: MainComponent },
  { path: 'dashboard', component: GameUiDashboardComponent },
  { path: 'preparegame', component: PrepareGameComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
