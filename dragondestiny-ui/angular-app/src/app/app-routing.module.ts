import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './dragondestiny-ui-login/signup/signup.component';
import { LoginInComponent } from './dragondestiny-ui-login/login-in/login-in.component';
import { MainComponent } from './dragondestiny-ui-playboard/main/main.component';
import { DashboardComponent } from './dragondestiny-ui-dashboard/dashboard.component';
import { PrepareGameComponent } from './dragondestiny-ui-prepare-game/prepare-game.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  { path: 'login', component: LoginInComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'main', component: MainComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'preparegame', component: PrepareGameComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule{

}
