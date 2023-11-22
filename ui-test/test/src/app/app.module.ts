import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { MaintestComponent } from './maintest/maintest.component';

@NgModule({
  declarations: [
    MaintestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [MaintestComponent]
})
export class AppModule { }
