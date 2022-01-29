import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllMerchantComponent } from './all-merchant.component';

describe('AllMerchantComponent', () => {
  let component: AllMerchantComponent;
  let fixture: ComponentFixture<AllMerchantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllMerchantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllMerchantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
