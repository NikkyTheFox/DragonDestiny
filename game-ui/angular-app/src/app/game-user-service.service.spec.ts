import { TestBed } from '@angular/core/testing';

import { GameUserServiceService } from './game-user-service.service';

describe('GameUserServiceService', () => {
  let service: GameUserServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GameUserServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
