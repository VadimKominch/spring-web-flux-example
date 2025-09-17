import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SseDisplayComponent } from './sse-display';

describe('SseDisplayComponent', () => {
  let component: SseDisplayComponent;
  let fixture: ComponentFixture<SseDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SseDisplayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SseDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
