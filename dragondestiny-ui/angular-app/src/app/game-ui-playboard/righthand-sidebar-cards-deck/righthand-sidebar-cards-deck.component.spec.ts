import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarCardsDeckComponent } from './righthand-sidebar-cards-deck.component';

describe('RighthandSidebarCardsDeckComponent', () => {
  let component: RighthandSidebarCardsDeckComponent;
  let fixture: ComponentFixture<RighthandSidebarCardsDeckComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarCardsDeckComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarCardsDeckComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
