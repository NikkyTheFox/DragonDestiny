import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameUiDashboardComponent } from './game-ui-dashboard.component';

describe('GameUiDashboardComponent', () => {
  let component: GameUiDashboardComponent;
  let fixture: ComponentFixture<GameUiDashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GameUiDashboardComponent]
    });
    fixture = TestBed.createComponent(GameUiDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
