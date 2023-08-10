import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionBoardFieldPawnComponent } from './main-section-board-field-pawn.component';

describe('MainSectionBoardFieldPawnComponent', () => {
  let component: MainSectionBoardFieldPawnComponent;
  let fixture: ComponentFixture<MainSectionBoardFieldPawnComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionBoardFieldPawnComponent]
    });
    fixture = TestBed.createComponent(MainSectionBoardFieldPawnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
