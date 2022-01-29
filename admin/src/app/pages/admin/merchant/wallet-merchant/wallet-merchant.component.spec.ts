import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WalletMerchantComponent } from './wallet-merchant.component';

describe('WalletMerchantComponent', () => {
  let component: WalletMerchantComponent;
  let fixture: ComponentFixture<WalletMerchantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WalletMerchantComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WalletMerchantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
