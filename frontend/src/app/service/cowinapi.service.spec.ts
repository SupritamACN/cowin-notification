import { TestBed } from '@angular/core/testing';

import { CowinapiService } from './cowinapi.service';

describe('CowinapiService', () => {
  let service: CowinapiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CowinapiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
