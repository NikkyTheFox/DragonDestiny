import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectCharacterComponent } from './select-character.component';

describe('SelectCharacterComponent', () => {
  let component: SelectCharacterComponent;
  let fixture: ComponentFixture<SelectCharacterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SelectCharacterComponent]
    });
    fixture = TestBed.createComponent(SelectCharacterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
