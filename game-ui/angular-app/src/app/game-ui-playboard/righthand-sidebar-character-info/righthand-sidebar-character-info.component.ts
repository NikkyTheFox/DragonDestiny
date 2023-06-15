import {Component, Input} from '@angular/core';
import {GameCharacter} from "../../game-character";
import {GameServiceService} from "../../game-service.service";

@Component({
  selector: 'app-righthand-sidebar-character-info',
  templateUrl: './righthand-sidebar-character-info.component.html',
  styleUrls: ['./righthand-sidebar-character-info.component.css']
})
export class RighthandSidebarCharacterInfoComponent {
  @Input() gameId!: number;
  @Input() playerId!: number;
  character: GameCharacter = {
    id: 0,
    name: "",
    profession: "",
    story: "",
    initialStrength: 0,
    initialHealth: 0,
  }

  constructor(private gameService: GameServiceService) {
  }

  ngOnInit(){
    this.gameService.getGameCharacter(this.gameId, this.playerId).subscribe( (data: GameCharacter) =>{
      this.character = data;
    })
  }
}
