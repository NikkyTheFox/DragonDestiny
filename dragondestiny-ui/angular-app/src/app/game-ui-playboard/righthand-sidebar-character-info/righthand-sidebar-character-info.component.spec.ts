import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarCharacterInfoComponent } from './righthand-sidebar-character-info.component';

describe('RighthandSidebarCharacterInfoComponent', () => {
  let component: RighthandSidebarCharacterInfoComponent;
  let fixture: ComponentFixture<RighthandSidebarCharacterInfoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarCharacterInfoComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarCharacterInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
