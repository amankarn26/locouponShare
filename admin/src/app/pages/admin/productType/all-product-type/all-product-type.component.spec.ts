import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllProductTypeComponent } from './all-product-type.component';

describe('AllProductTypeComponent', () => {
  let component: AllProductTypeComponent;
  let fixture: ComponentFixture<AllProductTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllProductTypeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllProductTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
