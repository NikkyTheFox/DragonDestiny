import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderLeftSectionComponent } from './header-left-section.component';

describe('HeaderLeftSectionComponent', () => {
  let component: HeaderLeftSectionComponent;
  let fixture: ComponentFixture<HeaderLeftSectionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderLeftSectionComponent]
    });
    fixture = TestBed.createComponent(HeaderLeftSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
