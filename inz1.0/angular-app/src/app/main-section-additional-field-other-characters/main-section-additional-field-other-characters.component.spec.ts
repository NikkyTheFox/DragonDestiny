import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionAdditionalFieldOtherCharactersComponent } from './main-section-additional-field-other-characters.component';

describe('MainSectionAdditionalFieldOtherCharactersComponent', () => {
  let component: MainSectionAdditionalFieldOtherCharactersComponent;
  let fixture: ComponentFixture<MainSectionAdditionalFieldOtherCharactersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionAdditionalFieldOtherCharactersComponent]
    });
    fixture = TestBed.createComponent(MainSectionAdditionalFieldOtherCharactersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
