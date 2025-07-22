import { TestBed } from '@angular/core/testing';

import { ShortenerService } from './shortenerService';

describe('Shortener', () => {
  let service: ShortenerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShortenerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
