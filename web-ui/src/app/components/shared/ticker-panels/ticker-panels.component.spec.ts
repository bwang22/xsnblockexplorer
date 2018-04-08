import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TickerPanelsComponent } from './ticker-panels.component';

describe('TickerPanelsComponent', () => {
  let component: TickerPanelsComponent;
  let fixture: ComponentFixture<TickerPanelsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TickerPanelsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TickerPanelsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
