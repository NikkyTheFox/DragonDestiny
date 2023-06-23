import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderMidSectionComponent } from './header-mid-section.component';

describe('HeaderMidSectionComponent', () => {
  let component: HeaderMidSectionComponent;
  let fixture: ComponentFixture<HeaderMidSectionComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HeaderMidSectionComponent]
    });
    fixture = TestBed.createComponent(HeaderMidSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
