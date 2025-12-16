import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccessoriesList } from './accessories-list';

describe('AccessoriesList', () => {
  let component: AccessoriesList;
  let fixture: ComponentFixture<AccessoriesList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccessoriesList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccessoriesList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
