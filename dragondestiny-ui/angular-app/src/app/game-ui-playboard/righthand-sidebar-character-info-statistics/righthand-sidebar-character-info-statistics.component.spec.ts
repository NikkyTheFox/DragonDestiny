import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarCharacterInfoStatisticsComponent } from './righthand-sidebar-character-info-statistics.component';

describe('RighthandSidebarCharacterInfoStatisticsComponent', () => {
  let component: RighthandSidebarCharacterInfoStatisticsComponent;
  let fixture: ComponentFixture<RighthandSidebarCharacterInfoStatisticsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarCharacterInfoStatisticsComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarCharacterInfoStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
