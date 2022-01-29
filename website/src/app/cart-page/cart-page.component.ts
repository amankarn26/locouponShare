import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartServiceService } from '../cart/cart-service.service';
import { RestService } from '../rest.service';
import Swal from 'sweetalert2'

declare var Razorpay: any;

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.scss']
})
export class CartPageComponent implements OnInit {

  cartItem = this.cartService.getCartCount();
  cartData = this.cartService.getCart();
  cartTotal = this.cartService.getCartTotal();

  islogin = localStorage.getItem('islogin');

  // For Order Booking
  public order = {
    branch: localStorage.getItem('selectedFirm'),
    customer: localStorage.getItem('id'),
    comments: '',
    adddelivery: 'YES',
    qcost: {
      totalitem: 0,
      baseprice: 0,
      gstamt: 0,
      delivery: 0,
      finalamt: 0,
      discountrate: 0,
      discountvalue: 0,
      servicecharge: 0,
      transferamt: 0,
      refundamt: 0,
      tip: 0


    },
    status: 'PLACED',      // -- enum PLACED/ACCEPTED/IN_PROCESS / PACKED / DELIVERED
    expdelivery: '2020-05-28',   // how to calculate
    expdeliverytime: '07:00:00', // how to calculate

    paymentmode: 'BANK', //  --- always BANK (because we don't have cash on delivery provision)
    favourite: 'NO',     //  -- YES/NO default NO.
    //  'deliverypool': '67', // -- if usedeliverypool = YES, assign person for delivery.
    transactionid: '',
    oentry: [{}]

  };

  private oentry = [{}];

  constructor(private cartService: CartServiceService, private restService: RestService, private route: Router) { }

  ngOnInit(): void {
    this.populateOrder()
    this.getDeliveryCharger()

    this.islogin = localStorage.getItem('islogin');
  }

  public removeProduct(index:number) {
    this.cartService.removeProduct(index);
    this.cartItem = this.cartService.getCartCount();
    this.cartData = this.cartService.getCart();
    this.cartTotal = this.cartService.getCartTotal();
  }

  public ClearAll() {
    this.cartService.clearAll();
    location.reload();
  }

  public inc(index: number) {
    this.cartService.addProductItemsInCart(index);
    this.cartItem = this.cartService.getCartCount();
    this.cartData = this.cartService.getCart();
    this.cartTotal = this.cartService.getCartTotal();

    this.populateOrder()
  }

  public dec(index: number) {
    this.cartService.subProductItemsInCart(index);
    this.cartItem = this.cartService.getCartCount();
    this.cartData = this.cartService.getCart();
    this.cartTotal = this.cartService.getCartTotal();

    this.populateOrder();
  }

  private getDeliveryCharger() {
    const urlstr = "?branch=" + localStorage.getItem('selectedFirm') + "&customer=" + localStorage.getItem('id');
    const url = this.restService.orderRest('getDeliveryCharge', urlstr);
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        if (response.status === 'Success') {
          this.order.qcost.delivery = Number(response.charge);
          this.order.qcost.finalamt += this.order.qcost.delivery
        } else {
          console.log('Delivery Person Not Available');
        }
      },
      error => {
        console.log('Error in data load.');
        
      }
    );
  }

  // For Order
  private populateOrder() {
    this.oentry = [];

    if (this.cartData) {
      for(let item of this.cartData) {
        const obj = {
          pname: item.name,
          variant: item.variant,
          brand: item.brand,
          stocksoldas: item.fullloose,
          rateperunit: item.price,
          rateunit: item.rateunit,
          qty: Number(item.quantity),
          amount: Number(item.ratewithgst) * Number(item.quantity),
          gst: Number(item.gst),
          gstamt: Number(item.gstamt),
          volume: Number(item.volume),
          entrytotal: '1'
        };

        if (this.oentry) {
          this.oentry.push(JSON.parse(JSON.stringify(obj)));
        } else {
          this.oentry = [JSON.parse(JSON.stringify(obj))];
        }
        
      }

      this.order.qcost.totalitem = Number(this.cartData.length);
      this.order.qcost.baseprice = this.cartData.reduce((a, b) => {
        return a + (b.quantity * b.price)
      }, 0);
      this.order.qcost.gstamt = this.cartData.reduce((a, b) => {
        return a + b.gstamt
      }, 0);
      this.order.qcost.finalamt = this.cartData.reduce((a, b) => {
        return a + b.amount
      }, 0) + this.order.qcost.delivery;

      this.order.oentry = this.oentry;
    }
  }

  initPay() {


    // payment json
    this.order.qcost.finalamt = Math.ceil(this.order.qcost.finalamt);
    let options: any = {
      key: 'rzp_test_dTV5HlVstVMqVg', // Enter the Key ID generated from the Dashboard
      amount: this.order.qcost.finalamt * 100, // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
      currency: 'INR',
      name: 'locoupons.',
      description: 'Test Transaction',
      image: 'http://45.32.62.106:8090/product/assets/logo/logo.png',
      handler: this.submitData.bind(this),
      prefill: {
        name: localStorage.getItem('name'),
        email: localStorage.getItem('email'),
        contact: localStorage.getItem('phone'), 
      },
      notes: {
        address: 'Razorpay Corporate Office'
      },
      theme: {
        color: '#F37254'
      }
    };


    var rzp1 = new Razorpay(options);
    rzp1.open();
  }

  submitData(response: any) {
    this.order.transactionid = response.razorpay_payment_id;
    
    this.order.branch = localStorage.getItem('selectedFirm')

    Swal.fire({
      icon: 'info',
      title: 'Please wait',
      text: "We are booking you order",
      allowOutsideClick: false,
      showConfirmButton: false
    })

    const url = this.restService.orderRest('add', '');
    this.restService.postFormData(url, this.order).subscribe(
      (response: any) => {
        this.cartService.clearAll();

        Swal.fire({
          icon: 'success',
          title: 'Your Order booked!',
          text: "Enjoy Shopping",
          showConfirmButton: false,
          timer: 1500,
          willClose: () => {
            this.route.navigate(['/']);
          }
        })
        

        // location.reload();

      }, error => {
        console.log("Order Not done")

        this.cartService.clearAll();
        // Swal.fire({
        //   icon: 'error',
        //   title: 'Order Not Booked!',
        //   text: "Please contact us for refund. If money debited.",
        //   showConfirmButton: false,
        //   timer: 5000,
        //   willClose: () => {
        //     this.route.navigate(['/']);
        //   }
        // })

        Swal.fire({
          icon: 'success',
          title: 'Your Order booked!',
          text: "Enjoy Shopping",
          showConfirmButton: false,
          timer: 1500,
          willClose: () => {
            this.route.navigate(['/']);
          }
        })
      }
    );
  }
}
