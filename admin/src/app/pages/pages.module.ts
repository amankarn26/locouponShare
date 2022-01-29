import { NgModule } from '@angular/core';
import { NbActionsModule, NbButtonModule, NbCardModule, NbCheckboxModule, NbIconModule, NbInputModule, NbMenuModule, NbRadioModule, NbSelectModule, NbStepperModule, NbUserModule } from '@nebular/theme';

import { ThemeModule } from '../@theme/theme.module';
import { PagesComponent } from './pages.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { ECommerceModule } from './e-commerce/e-commerce.module';
import { PagesRoutingModule } from './pages-routing.module';
import { MiscellaneousModule } from './miscellaneous/miscellaneous.module';
import { AllCustomerComponent } from './admin/customer/all-customer/all-customer.component';
import { TablesRoutingModule } from './tables/tables-routing.module';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { AllEmployeeComponent } from './admin/employee/all-employee/all-employee.component';
import { AllMerchantComponent } from './admin/merchant/all-merchant/all-merchant.component';
import { AllCategoryComponent } from './admin/category/all-category/all-category.component';
import { AllProductTypeComponent } from './admin/productType/all-product-type/all-product-type.component';
import { AllProductsComponent } from './admin/products/all-products/all-products.component';
import { AddEmployeeComponent } from './admin/employee/add-employee/add-employee.component';
import { FormsRoutingModule } from './forms/forms-routing.module';
import { FormsModule as ngFormsModule } from '@angular/forms';
import { AddMerchantComponent } from './admin/merchant/add-merchant/add-merchant.component';
import { AddCategoryComponent } from './admin/category/add-category/add-category.component';
import { AddProductsComponent } from './admin/products/add-products/add-products.component';
import { AddProductTypeComponent } from './admin/productType/add-product-type/add-product-type.component';
import { WalletMerchantComponent } from './admin/merchant/wallet-merchant/wallet-merchant.component';
import { SettelmentMerchentComponent } from './admin/merchant/settelment-merchent/settelment-merchent.component';

@NgModule({
  imports: [
    PagesRoutingModule,
    ThemeModule,
    NbMenuModule,
    DashboardModule,
    ECommerceModule,
    MiscellaneousModule,
    TablesRoutingModule,
    Ng2SmartTableModule,
    NbCardModule,
    NbStepperModule,
    NbInputModule,
    NbButtonModule,
    NbActionsModule,
    NbUserModule,
    NbCheckboxModule,
    NbRadioModule,
    FormsRoutingModule,
    NbSelectModule,
    NbIconModule,
    ngFormsModule,
  ],
  declarations: [
    PagesComponent,
    AllCustomerComponent,
    AllEmployeeComponent,
    AllMerchantComponent,
    AllCategoryComponent,
    AllProductTypeComponent,
    AllProductsComponent,
    AddEmployeeComponent,
    AddMerchantComponent,
    AddCategoryComponent,
    AddProductsComponent,
    AddProductTypeComponent,
    WalletMerchantComponent,
    SettelmentMerchentComponent,
  ],
})
export class PagesModule {
}
