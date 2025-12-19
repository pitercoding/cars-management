import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnersDetails } from './owners-details';

describe('OwnersDetails', () => {
  let component: OwnersDetails;
  let fixture: ComponentFixture<OwnersDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OwnersDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnersDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
