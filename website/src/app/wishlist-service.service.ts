import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class WishlistServiceService {
  private wishlist: any[] | undefined;

  public product = {
    id: 19174,
    name: '',
    img: '',
    price: 0,
    date: '',
    variant: '',
  };

  constructor() {
    let wishData = localStorage.getItem('wishData');
    if (wishData) {
      this.wishlist = JSON.parse(wishData);
    }
  }

  getCart() {
    return this.wishlist;
  }

  getCartCount() {
    return new Set(this.wishlist).size;
  }

  getCartTotal() {
    if (this.wishlist) {
      let totalAmount = 0
      for(let item of this.wishlist) {
        totalAmount += item.amount
      }
      return totalAmount
    }
    else return 0
  }

  addProduct(getproduct : any) {
    // const result1 = this.cart.find(
    //   (x: {id: any, mfg: any}) => x.id == product.id && x.mfg == product.mfg
    // );
    this.product = getproduct;
    
    if (this.wishlist) {
      this.wishlist.push(this.product);
    } else {
      this.wishlist = [this.product]
    }
    

    localStorage.setItem("wishData", JSON.stringify(this.wishlist));
  }

  removeProduct(removalindex: number) {
    if (this.wishlist) {
      this.wishlist.splice(removalindex, 1);

      localStorage.setItem("wishData", JSON.stringify(this.wishlist));
    }
  }

  clearAll() {
    this.wishlist = [];
    localStorage.setItem("wishData", JSON.stringify(this.wishlist));
  }
}
