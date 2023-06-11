import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarCharacterInfoStatisticsStatComponent } from './righthand-sidebar-character-info-statistics-stat.component';

describe('RighthandSidebarCharacterInfoStatisticsStatComponent', () => {
  let component: RighthandSidebarCharacterInfoStatisticsStatComponent;
  let fixture: ComponentFixture<RighthandSidebarCharacterInfoStatisticsStatComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarCharacterInfoStatisticsStatComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarCharacterInfoStatisticsStatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
