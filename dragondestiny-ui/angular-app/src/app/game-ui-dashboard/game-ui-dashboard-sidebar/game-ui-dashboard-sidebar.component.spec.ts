import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameUiDashboardSidebarComponent } from './game-ui-dashboard-sidebar.component';

describe('GameUiDashboardSidebarComponent', () => {
  let component: GameUiDashboardSidebarComponent;
  let fixture: ComponentFixture<GameUiDashboardSidebarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GameUiDashboardSidebarComponent]
    });
    fixture = TestBed.createComponent(GameUiDashboardSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
