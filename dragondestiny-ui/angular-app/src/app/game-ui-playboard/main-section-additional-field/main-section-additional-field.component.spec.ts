import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionAdditionalFieldComponent } from './main-section-additional-field.component';

describe('MainSectionAdditionalFieldComponent', () => {
  let component: MainSectionAdditionalFieldComponent;
  let fixture: ComponentFixture<MainSectionAdditionalFieldComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionAdditionalFieldComponent]
    });
    fixture = TestBed.createComponent(MainSectionAdditionalFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
