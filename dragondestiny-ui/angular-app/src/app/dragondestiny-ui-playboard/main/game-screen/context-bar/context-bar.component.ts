import { Component, Input } from '@angular/core';
import { RequestStructureGameidPlayerlogin } from '../../../../interfaces/request-structure-gameid-playerlogin';

@Component({
  selector: 'app-context-bar',
  templateUrl: './context-bar.component.html',
  styleUrls: ['./context-bar.component.css']
})
export class ContextBarComponent {
  @Input() requestStructure!: RequestStructureGameidPlayerlogin;
}
