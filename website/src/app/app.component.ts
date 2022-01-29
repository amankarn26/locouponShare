import { DOCUMENT } from '@angular/common';
import { Component, HostListener, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartServiceService } from './cart/cart-service.service';
import { CollapseModule, WavesModule } from 'angular-bootstrap-md'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'welcome';

  cartItem = this.cartService.getCartCount();
  cartData = this.cartService.getCart();
  cartTotal = this.cartService.getCartTotal();
  stickNav = false;
  firstMenuOpen = false;
  secondMenuOpen = false;

  islogin = localStorage.getItem('islogin');
  getRole = localStorage.getItem('role');

  custname = localStorage.getItem('name');

  constructor(
    @Inject(DOCUMENT) document: Document,
    private cartService: CartServiceService,
    private router: Router
  ) {
    setInterval(() => {
      this.refreshData();
    }, 2000);
  }

  ngOnInit() {
    this.router.navigate(['/']);
    if (this.islogin == "true") {
      if(this.getRole != "CUSTOMER") {
        // location.href = "http://45.32.62.106:8090/medlife/"
      }
    }
  }

  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (
      document.body.scrollTop > 200 ||
      document.documentElement.scrollTop > 200
    ) {
      this.stickNav = true;
    } else {
      this.stickNav = false;
    }

    this.cartItem = this.cartService.getCartCount();
    this.cartData = this.cartService.getCart();
  }

  private refreshData() {
    this.cartItem = this.cartService.getCartCount();
    this.cartData = this.cartService.getCart();
    this.cartTotal = this.cartService.getCartTotal();
  }

  public removeProduct(index: number) {
    this.cartService.removeProduct(index);
    this.cartItem = this.cartService.getCartCount();
    this.cartData = this.cartService.getCart();
    this.cartTotal = this.cartService.getCartTotal();
  }

  public navigateToCategory(additionalPaths: string) {
    this.firstMenuOpen = false
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/category', additionalPaths]);
    });
  }

  public moveToProfile(profileType:number) {
    if (localStorage.getItem('islogin') == "true") {
      switch (profileType) {
        case 1:
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/profile']);
          });
          break;
        case 2:
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/order']);
          });
          break;
        case 3:
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/transection']);
          });
          break;
      
        default:
          break;
      }
    }
    else {
      this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
        this.router.navigate(['/login']);
      });
    }
  }

  public logout() {
    localStorage.clear()
    sessionStorage.clear()
    location.reload()
  }

  public openSecondMenu() {
    this.secondMenuOpen = !this.secondMenuOpen
  }

  public gotoCart() {
    if(this.secondMenuOpen)
      this.router.navigate(['cart'])
    this.secondMenuOpen = false
  }

  public openFirstMenu() {
    this.firstMenuOpen = !this.firstMenuOpen
  }
}
