import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarGameControlConfirmComponent } from './righthand-sidebar-game-control-confirm.component';

describe('RighthandSidebarGameControlConfirmComponent', () => {
  let component: RighthandSidebarGameControlConfirmComponent;
  let fixture: ComponentFixture<RighthandSidebarGameControlConfirmComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarGameControlConfirmComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarGameControlConfirmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
