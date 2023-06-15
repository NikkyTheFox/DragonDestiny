import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionBoardFieldInnerComponent } from './main-section-board-field-inner.component';

describe('MainSectionBoardFieldInnerComponent', () => {
  let component: MainSectionBoardFieldInnerComponent;
  let fixture: ComponentFixture<MainSectionBoardFieldInnerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionBoardFieldInnerComponent]
    });
    fixture = TestBed.createComponent(MainSectionBoardFieldInnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
