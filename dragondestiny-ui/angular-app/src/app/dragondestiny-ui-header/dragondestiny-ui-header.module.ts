import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from "./header/header.component";
import { HeaderLeftComponent } from "./header-left/header-left.component";
import { HeaderMiddleComponent } from "./header-middle/header-middle.component";
import { HeaderRightComponent } from "./header-right/header-right.component";
import { RouterLink } from "@angular/router";



@NgModule({
  declarations: [
    HeaderComponent,
    HeaderLeftComponent,
    HeaderMiddleComponent,
    HeaderRightComponent
  ],
  imports: [
    CommonModule,
    RouterLink
  ],
  exports: [
    HeaderComponent,
    HeaderLeftComponent,
    HeaderMiddleComponent,
    HeaderRightComponent
  ]
})
export class DragondestinyUiHeaderModule { }
