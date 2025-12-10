import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BrandsDetails } from './brands-details';

describe('BrandsDetails', () => {
  let component: BrandsDetails;
  let fixture: ComponentFixture<BrandsDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BrandsDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BrandsDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
