import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-righthand-sidebar-character-info-statistics-stat',
  templateUrl: './righthand-sidebar-character-info-statistics-stat.component.html',
  styleUrls: ['./righthand-sidebar-character-info-statistics-stat.component.css']
})
export class RighthandSidebarCharacterInfoStatisticsStatComponent {
  @Input() statName!:string;
  @Input() statValue!:number;

}
