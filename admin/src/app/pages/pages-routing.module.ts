import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ECommerceComponent } from './e-commerce/e-commerce.component';
import { NotFoundComponent } from './miscellaneous/not-found/not-found.component';
import { AllCustomerComponent } from './admin/customer/all-customer/all-customer.component';
import { AllEmployeeComponent } from './admin/employee/all-employee/all-employee.component';
import { AllMerchantComponent } from './admin/merchant/all-merchant/all-merchant.component';
import { AllCategoryComponent } from './admin/category/all-category/all-category.component';
import { AllProductsComponent } from './admin/products/all-products/all-products.component';
import { AllProductTypeComponent } from './admin/productType/all-product-type/all-product-type.component';
import { AddEmployeeComponent } from './admin/employee/add-employee/add-employee.component';
import { AddMerchantComponent } from './admin/merchant/add-merchant/add-merchant.component';
import { AddCategoryComponent } from './admin/category/add-category/add-category.component';
import { AddProductTypeComponent } from './admin/productType/add-product-type/add-product-type.component';
import { AddProductsComponent } from './admin/products/add-products/add-products.component';

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'customer',
      component: AllCustomerComponent,
    },
    {
      path: 'merchant',
      component: AllMerchantComponent,
    },
    {
      path: 'addMerchant',
      component: AddMerchantComponent,
    },
    {
      path: 'employee',
      component: AllEmployeeComponent,
    },
    {
      path: 'addEmployee',
      component: AddEmployeeComponent,
    },
    {
      path: 'category',
      component: AllCategoryComponent,
    },
    {
      path: 'addCategory',
      component: AddCategoryComponent,
    },
    {
      path: 'productType',
      component: AllProductTypeComponent,
    },
    {
      path: 'addProductType',
      component: AddProductTypeComponent,
    },
    {
      path: 'products',
      component: AllProductsComponent,
    },
    {
      path: 'addProducts',
      component: AddProductsComponent,
    },
    {
      path: 'dashboard',
      component: ECommerceComponent,
    },
    {
      path: 'iot-dashboard',
      component: DashboardComponent,
    },
    {
      path: 'layout',
      loadChildren: () => import('./layout/layout.module')
        .then(m => m.LayoutModule),
    },
    {
      path: 'forms',
      loadChildren: () => import('./forms/forms.module')
        .then(m => m.FormsModule),
    },
    {
      path: 'ui-features',
      loadChildren: () => import('./ui-features/ui-features.module')
        .then(m => m.UiFeaturesModule),
    },
    {
      path: 'modal-overlays',
      loadChildren: () => import('./modal-overlays/modal-overlays.module')
        .then(m => m.ModalOverlaysModule),
    },
    {
      path: 'extra-components',
      loadChildren: () => import('./extra-components/extra-components.module')
        .then(m => m.ExtraComponentsModule),
    },
    {
      path: 'maps',
      loadChildren: () => import('./maps/maps.module')
        .then(m => m.MapsModule),
    },
    {
      path: 'charts',
      loadChildren: () => import('./charts/charts.module')
        .then(m => m.ChartsModule),
    },
    {
      path: 'editors',
      loadChildren: () => import('./editors/editors.module')
        .then(m => m.EditorsModule),
    },
    {
      path: 'tables',
      loadChildren: () => import('./tables/tables.module')
        .then(m => m.TablesModule),
    },
    {
      path: 'miscellaneous',
      loadChildren: () => import('./miscellaneous/miscellaneous.module')
        .then(m => m.MiscellaneousModule),
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    },
    {
      path: '**',
      component: NotFoundComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}
