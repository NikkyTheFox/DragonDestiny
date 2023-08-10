import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarGameControlOptionsComponent } from './righthand-sidebar-game-control-options.component';

describe('RighthandSidebarGameControlOptionsComponent', () => {
  let component: RighthandSidebarGameControlOptionsComponent;
  let fixture: ComponentFixture<RighthandSidebarGameControlOptionsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarGameControlOptionsComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarGameControlOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
