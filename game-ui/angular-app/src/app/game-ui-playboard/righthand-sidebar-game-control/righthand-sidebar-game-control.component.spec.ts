import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarGameControlComponent } from './righthand-sidebar-game-control.component';

describe('RighthandSidebarGameControlComponent', () => {
  let component: RighthandSidebarGameControlComponent;
  let fixture: ComponentFixture<RighthandSidebarGameControlComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarGameControlComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarGameControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
