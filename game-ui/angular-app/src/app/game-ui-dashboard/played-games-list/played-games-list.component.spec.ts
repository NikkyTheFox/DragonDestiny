import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayedGamesListComponent } from './played-games-list.component';

describe('PlayedGamesListComponent', () => {
  let component: PlayedGamesListComponent;
  let fixture: ComponentFixture<PlayedGamesListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlayedGamesListComponent]
    });
    fixture = TestBed.createComponent(PlayedGamesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
