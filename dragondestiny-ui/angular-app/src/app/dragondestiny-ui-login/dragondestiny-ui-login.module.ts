import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginInComponent } from './login-in/login-in.component';
import { SignupComponent } from './signup/signup.component';
import { FormsModule } from '@angular/forms';
import { DragondestinyUiLoginRoutingModule } from './dragondestiny-ui-login-routing.module';



@NgModule({
  declarations: [
    LoginInComponent,
    SignupComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    DragondestinyUiLoginRoutingModule,
  ]
})
export class DragondestinyUiLoginModule { }
