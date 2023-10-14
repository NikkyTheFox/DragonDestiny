import { RouterModule, Routes } from '@angular/router';
import { LoginInComponent } from './login-in/login-in.component';
import { SignupComponent } from './signup/signup.component';
import { NgModule } from '@angular/core';


const routes: Routes = [
  { path: 'login', component: LoginInComponent },
  { path: 'signup', component: SignupComponent },
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DragondestinyUiLoginRoutingModule{

}
