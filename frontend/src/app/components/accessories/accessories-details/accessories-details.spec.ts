import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccessoriesDetailss } from './accessories-details';

describe('AccessoriesDetailss', () => {
  let component: AccessoriesDetailss;
  let fixture: ComponentFixture<AccessoriesDetailss>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccessoriesDetailss]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccessoriesDetailss);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
