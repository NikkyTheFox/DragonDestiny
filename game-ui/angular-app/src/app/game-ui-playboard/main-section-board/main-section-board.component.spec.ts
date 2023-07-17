import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionBoardComponent } from './main-section-board.component';

describe('MainSectionBoardComponent', () => {
  let component: MainSectionBoardComponent;
  let fixture: ComponentFixture<MainSectionBoardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionBoardComponent]
    });
    fixture = TestBed.createComponent(MainSectionBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
