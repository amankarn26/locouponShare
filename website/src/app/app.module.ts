import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { IvyCarouselModule } from 'angular-responsive-carousel';
import { HomepageComponent } from './homepage/homepage.component';
import { HttpClientModule } from '@angular/common/http';
import { CategoryComponent } from './category/category.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { CartPageComponent } from './cart-page/cart-page.component';
import { AboutComponent } from './company/about/about.component';
import { TermComponent } from './company/term/term.component';
import { PrivacyComponent } from './company/privacy/privacy.component';
import { ContactComponent } from './company/contact/contact.component';
import { LoginComponent } from './login/login.component';
import { DatePipe, HashLocationStrategy, LocationStrategy } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { SearchComponent } from './search/search.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    CategoryComponent,
    ProductDetailsComponent,
    CartPageComponent,
    AboutComponent,
    TermComponent,
    PrivacyComponent,
    ContactComponent,
    LoginComponent,
    ProfileComponent,
    WishlistComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    IvyCarouselModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MDBBootstrapModule.forRoot()
  ],
  providers: [
    DatePipe,
    {provide : LocationStrategy , useClass: HashLocationStrategy}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
