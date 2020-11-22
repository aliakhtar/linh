import { TestBed } from '@angular/core/testing';

import { CmdListService } from './cmd-list.service';

describe('CmdService', () => {
  let service: CmdListService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CmdListService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
