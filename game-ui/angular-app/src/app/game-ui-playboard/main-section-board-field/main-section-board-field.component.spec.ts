import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionBoardFieldComponent } from './main-section-board-field.component';

describe('MainSectionBoardFieldComponent', () => {
  let component: MainSectionBoardFieldComponent;
  let fixture: ComponentFixture<MainSectionBoardFieldComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionBoardFieldComponent]
    });
    fixture = TestBed.createComponent(MainSectionBoardFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
