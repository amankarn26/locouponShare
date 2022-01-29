import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestService } from '../rest.service';
import Swal from 'sweetalert2'
import { WishlistServiceService } from '../wishlist-service.service';

declare var Razorpay: any;

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {

  cartItem = this.wishService.getCartCount();
  cartData = this.wishService.getCart();
  cartTotal = this.wishService.getCartTotal();

  islogin = localStorage.getItem('islogin');


  constructor(private wishService: WishlistServiceService, private restService: RestService, private route: Router) { }

  ngOnInit(): void {
    this.islogin = localStorage.getItem('islogin');
  }

  public removeProduct(index:number) {
    this.wishService.removeProduct(index);
    this.cartItem = this.wishService.getCartCount();
    this.cartData = this.wishService.getCart();
    this.cartTotal = this.wishService.getCartTotal();
  }
}
