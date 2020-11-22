import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OutputItemComponent } from './output-item.component';

describe('OutputItemComponent', () => {
  let component: OutputItemComponent;
  let fixture: ComponentFixture<OutputItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OutputItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OutputItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
