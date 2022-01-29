import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CartPageComponent } from './cart-page/cart-page.component';
import { CategoryComponent } from './category/category.component';
import { AboutComponent } from './company/about/about.component';
import { ContactComponent } from './company/contact/contact.component';
import { PrivacyComponent } from './company/privacy/privacy.component';
import { TermComponent } from './company/term/term.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { ProfileComponent } from './profile/profile.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { SearchComponent } from './search/search.component';

const routes: Routes = [
  { path: '', component:  HomepageComponent,  },

  { path: 'category', component:  CategoryComponent },
  { path: 'category/:name', component:  CategoryComponent },

  { path: 'products/:id', component:  ProductDetailsComponent },

  { path: 'cart', component:  CartPageComponent },

  { path: 'wishlist', component:  WishlistComponent },

  { path: 'search', component:  SearchComponent },
  { path: 'search/:query', component:  SearchComponent },

  { path: 'login', component:  LoginComponent },

  { path: 'profile', component:  ProfileComponent },
  { path: 'order', component:  ProfileComponent },
  { path: 'transection', component:  ProfileComponent },

  { path: 'company/about', component:  AboutComponent },
  { path: 'company/contact', component:  ContactComponent },
  { path: 'company/privacy', component:  PrivacyComponent },
  { path: 'company/term', component:  TermComponent },


  { path: '**', component:  HomepageComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
