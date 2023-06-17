import {Component, Input} from '@angular/core';
import {Character} from "../../interfaces/game-engine/game-character";
import {GameEngineService} from "../../services/game-engine.service";

@Component({
  selector: 'app-righthand-sidebar-character-info',
  templateUrl: './righthand-sidebar-character-info.component.html',
  styleUrls: ['./righthand-sidebar-character-info.component.css']
})
export class RighthandSidebarCharacterInfoComponent {
  @Input() gameId!: number;
  @Input() playerId!: number;
  character: Character = {
    id: 0,
    name: "",
    profession: "",
    story: "",
    initialStrength: 0,
    initialHealth: 0,
  }

  constructor(private gameService: GameEngineService) {
  }

  ngOnInit(){
    this.gameService.getGameCharacter(this.gameId, this.playerId).subscribe( (data: Character) =>{
      this.character = data;
    })
  }
}
