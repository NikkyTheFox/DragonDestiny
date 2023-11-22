import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MaintestComponent } from './maintest.component';

describe('MaintestComponent', () => {
  let component: MaintestComponent;
  let fixture: ComponentFixture<MaintestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MaintestComponent]
    });
    fixture = TestBed.createComponent(MaintestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
