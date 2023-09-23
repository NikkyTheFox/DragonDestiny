import { Component, Input } from '@angular/core';
import { RequestStructureGameidPlayerlogin } from '../../../interfaces/request-structure-gameid-playerlogin'
@Component({
  selector: 'app-righthand-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;

}
