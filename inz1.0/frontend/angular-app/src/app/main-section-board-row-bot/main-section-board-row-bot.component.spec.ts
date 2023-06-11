import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionBoardRowBotComponent } from './main-section-board-row-bot.component';

describe('MainSectionBoardRowBotComponent', () => {
  let component: MainSectionBoardRowBotComponent;
  let fixture: ComponentFixture<MainSectionBoardRowBotComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionBoardRowBotComponent]
    });
    fixture = TestBed.createComponent(MainSectionBoardRowBotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
