import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrepareGameComponent } from './prepare-game.component';

const routes: Routes = [
  { path: 'preparegame', component: PrepareGameComponent }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DragondestinyUiPrepareGameRoutingModule{

}
