import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainSectionAdditionalFieldItemsComponent } from './main-section-additional-field-items.component';

describe('MainSectionAdditionalFieldItemsComponent', () => {
  let component: MainSectionAdditionalFieldItemsComponent;
  let fixture: ComponentFixture<MainSectionAdditionalFieldItemsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MainSectionAdditionalFieldItemsComponent]
    });
    fixture = TestBed.createComponent(MainSectionAdditionalFieldItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
