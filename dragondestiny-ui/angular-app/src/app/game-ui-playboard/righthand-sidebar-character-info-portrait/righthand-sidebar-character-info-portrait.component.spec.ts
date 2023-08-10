import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarCharacterInfoPortraitComponent } from './righthand-sidebar-character-info-portrait.component';

describe('RighthandSidebarCharacterInfoPortraitComponent', () => {
  let component: RighthandSidebarCharacterInfoPortraitComponent;
  let fixture: ComponentFixture<RighthandSidebarCharacterInfoPortraitComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarCharacterInfoPortraitComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarCharacterInfoPortraitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
