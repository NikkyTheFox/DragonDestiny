import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginInComponent } from './login-in.component';

describe('LoginInComponent', () => {
  let component: LoginInComponent;
  let fixture: ComponentFixture<LoginInComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginInComponent]
    });
    fixture = TestBed.createComponent(LoginInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
