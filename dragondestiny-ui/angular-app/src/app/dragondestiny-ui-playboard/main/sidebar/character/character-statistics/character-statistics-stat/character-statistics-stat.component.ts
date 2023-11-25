import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-character-statistics-stat',
  templateUrl: './character-statistics-stat.component.html',
  styleUrls: ['./character-statistics-stat.component.css']
})
export class CharacterStatisticsStatComponent{
  @Input() statName!:string;
  @Input() statValue!:number;
}
