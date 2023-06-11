import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionBoardRowTopComponent } from './main-section-board-row-top.component';

describe('MainSectionBoardRowTopComponent', () => {
  let component: MainSectionBoardRowTopComponent;
  let fixture: ComponentFixture<MainSectionBoardRowTopComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionBoardRowTopComponent]
    });
    fixture = TestBed.createComponent(MainSectionBoardRowTopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
