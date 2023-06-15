import {Component, Input} from '@angular/core';
import {GameCharacter} from "../../game-character";
import {GameServiceService} from "../../game-service.service";
@Component({
  selector: 'app-main-section-additional-field-other-characters',
  templateUrl: './main-section-additional-field-other-characters.component.html',
  styleUrls: ['./main-section-additional-field-other-characters.component.css']
})
export class MainSectionAdditionalFieldOtherCharactersComponent {
  @Input() gameId!: number;
  @Input() playerId!: number;
  otherCharacters: GameCharacter[];
  allCharacters: GameCharacter[];
  constructor(private gameService: GameServiceService) {
    this.otherCharacters = [];
    this.allCharacters = [];
  }

  ngOnInit(){
    this.gameService.getGameCharacters(this.gameId).subscribe( (data: any) => {
      this.allCharacters = data.characterList;
      this.retrieveOtherCharacters();
    })
  }


  retrieveOtherCharacters(){
    this.otherCharacters = this.allCharacters.filter(character => character.id !== this.playerId);
  }
}
