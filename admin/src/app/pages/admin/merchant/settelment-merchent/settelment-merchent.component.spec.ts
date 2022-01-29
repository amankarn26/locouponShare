import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SettelmentMerchentComponent } from './settelment-merchent.component';

describe('SettelmentMerchentComponent', () => {
  let component: SettelmentMerchentComponent;
  let fixture: ComponentFixture<SettelmentMerchentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SettelmentMerchentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SettelmentMerchentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
