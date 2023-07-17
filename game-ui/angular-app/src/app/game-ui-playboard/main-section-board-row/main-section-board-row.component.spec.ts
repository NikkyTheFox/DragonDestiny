import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionBoardRowComponent } from './main-section-board-row.component';

describe('MainSectionBoardRowComponent', () => {
  let component: MainSectionBoardRowComponent;
  let fixture: ComponentFixture<MainSectionBoardRowComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionBoardRowComponent]
    });
    fixture = TestBed.createComponent(MainSectionBoardRowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
