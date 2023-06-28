import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarGameControlDiceComponent } from './righthand-sidebar-game-control-dice.component';

describe('RighthandSidebarGameControlDiceComponent', () => {
  let component: RighthandSidebarGameControlDiceComponent;
  let fixture: ComponentFixture<RighthandSidebarGameControlDiceComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarGameControlDiceComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarGameControlDiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
