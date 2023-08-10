import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RighthandSidebarComponent } from './righthand-sidebar.component';

describe('RighthandSidebarComponent', () => {
  let component: RighthandSidebarComponent;
  let fixture: ComponentFixture<RighthandSidebarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RighthandSidebarComponent]
    });
    fixture = TestBed.createComponent(RighthandSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
