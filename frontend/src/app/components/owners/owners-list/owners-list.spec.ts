import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnersList } from './owners-list';

describe('OwnersList', () => {
  let component: OwnersList;
  let fixture: ComponentFixture<OwnersList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OwnersList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnersList);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
