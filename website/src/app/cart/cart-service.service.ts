import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CartServiceService {

  private cart: any[] | undefined;

  public product = {
    id: 19174,
    prodtype: "",
    name: "",
    rateunit: "ML",
    priority: 1,
    img: "",
    price: 0,
    quantity: 0,
    volume: 0,
    gst: 0,
    gstamt: 0,
    amount: 0,
    ratewithgst: 0,
    description:'',
    mfg: "",
    brand: "",
    fullloose: "",
    variant: ""
  };

  constructor() { 
    let cartData = localStorage.getItem("cartData")
    if (cartData) {
      this.cart = JSON.parse(cartData);
    }
  }

  getCart() {
    return this.cart;
  }

  getCartCount() {
    return new Set(this.cart).size;
  }

  getCartTotal() {
    if (this.cart) {
      let totalAmount = 0
      for(let item of this.cart) {
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
    
    if (this.cart) {
      this.cart.push(this.product);
    } else {
      this.cart = [this.product]
    }
    

    localStorage.setItem("cartData", JSON.stringify(this.cart));
  }

  removeProduct(removalindex: number) {
    if (this.cart) {
      this.cart.splice(removalindex, 1);

      localStorage.setItem("cartData", JSON.stringify(this.cart));
    }
  }

  clearAll() {
    this.cart = [];
    localStorage.setItem("cartData", JSON.stringify(this.cart));
  }

  addProductItemsInCart(index: number) {
    // this.cart[index].quantity++
    // this.cart[index].amount = this.cart[index].price * this.cart[index].quantity

    if (this.cart) {
      this.cart[index].quantity++
      this.cart[index].gstamt = ((this.cart[index].gst * this.cart[index].price) / 100) * this.cart[index].quantity
      this.cart[index].amount = this.cart[index].ratewithgst * this.cart[index].quantity

      localStorage.setItem('cartData', JSON.stringify(this.cart));
    }

  }

  subProductItemsInCart(index: number) {
    // this.cart[index].quantity--
    // this.cart[index].amount = this.cart[index].price * this.cart[index].quantity

    if (this.cart) {
      this.cart[index].quantity--
      this.cart[index].gstamt = ((this.cart[index].gst * this.cart[index].price) / 100) * this.cart[index].quantity
      this.cart[index].amount = this.cart[index].ratewithgst * this.cart[index].quantity
      localStorage.setItem('cartData', JSON.stringify(this.cart));
    }
    
  }
}
