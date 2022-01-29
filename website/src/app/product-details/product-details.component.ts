import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CartServiceService } from '../cart/cart-service.service';
import { RestService } from '../rest.service';
import Swal from 'sweetalert2'
import { DatePipe } from '@angular/common';
import { WishlistServiceService } from '../wishlist-service.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss'],
})
export class ProductDetailsComponent implements OnInit {
  public innerWidth: any;
  public selectedScreen: any;

  public productDetail: any;
  public productLoaded = false;

  public hotdeals: any;
  public hotdealsLoaded = false;

  public allProduct: any;
  public topProductLoaded = false;

  public isGoToCartShow = false;

  public product = {
    id: 19174,
    prodtype: '',
    name: '',
    rateunit: 'RATE_PER_PKT',
    priority: 1,
    img: '',
    price: 0,
    quantity: 0,
    volume: 0,
    gst: 0,
    gstamt: 0,
    amount: 0,
    ratewithgst: 0,
    description: '',
    mfg: '',
    brand: '',
    fullloose: '',
    variant: '',
  };

  public productDetails: any;

  public groupedByVariant: any;

  public VarinetData: any;

  public selectedStock: any;

  public isPageLoaded = false;

  public variant: any;
  public qty = 1;

  public prodID = 0;

  public inputQuery: any;

  public docServer = this.restService.docServer;

  constructor(
    private route: Router,
    public routprms: ActivatedRoute,
    private restService: RestService,
    private cartService: CartServiceService,
    private wishlist: WishlistServiceService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.innerWidth = window.innerWidth;
    this.setSelectedScreen();

    window.scrollTo(0, 0);
    this.prodID = this.routprms.snapshot.params['id'];
    if (this.prodID > 0) {
      this.getProductDetails();
      this.getCompleteProductDetails();
    }
    this.getPopularProducts();
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.innerWidth = window.innerWidth;
    this.setSelectedScreen();
  }

  public setSelectedScreen() {
    if (this.innerWidth > 1024) {
      this.selectedScreen = 1;
    } else if (this.innerWidth > 769) {
      this.selectedScreen = 2;
    } else {
      this.selectedScreen = 3;
    }
  }

  private getProductDetails() {
    // Get Information from JSON REST api
    // const url = this.restService.productPageRest('productbyid', this.prodID);
    // this.restService.getJSONFromURL(url).subscribe(
    //   (response: any) => {
    //     this.productDetail = response;
    //     if (response.name) {
    //       this.getSelectedProduct();
    //     }
    //     this.productLoaded = true;
    //     // console.log(this.productDetail)
    //   },
    //   (error) => {
    //     console.log('Error in data load.');
    //   }
    // );
    this.restService.allProduct.map(entry => {
      if (entry.id == this.prodID) {
        this.productDetail = entry
        this.productLoaded = true;
        this.getSelectedProduct();
      }
      return entry
    })
  }

  private getSelectedProduct() {
    // Get Information from JSON REST api
    // const url = this.restService.catPageRest(
    //   'getByProdType',
    //   this.productDetail.prodtype
    // );
    // this.restService.getJSONFromURL(url).subscribe(
    //   (response: any) => {
    //     this.allProduct = response;
    //     this.topProductLoaded = true;
    //   },
    //   (error) => {
    //     console.log('Error in data load.');
    //   }
    // );
    this.allProduct = this.restService.allProduct;
    this.topProductLoaded = true;
  }

  private getPopularProducts() {
    // Get Information from JSON REST api
    const url = this.restService.homePageRest('hotdeals', '');
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        this.hotdeals = response;
        this.hotdealsLoaded = true;
      },
      (error) => {
        console.log('Error in data load.');
      }
    );
  }

  // get product details

  private getCompleteProductDetails() {
    const url = this.restService.productPageRest(
      'getCompleteProductDetails',
      this.prodID
    );
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        if (response.length != 0) {
          if (response.pstock.length > 0) {
            // Get Data From server and store it
            this.productDetails = response;

            // Extract all stock and group it by branch
            // this.groupedByStock = this.groupByKey(this.productDetails.pstock, 'branchname'); console.log(this.groupedByStock)

            // // Get All Branch details from group
            // let viewData = [];
            // (Object.keys(this.groupedByStock)).forEach(entry => {
            //   viewData.push(
            //     {
            //       name: entry,
            //       id: this.groupedByStock[entry][0].branch,
            //       item: this.groupedByStock[entry].length,
            //     }
            //   )
            // });

            // // Set Branch from data
            // this.firmList = viewData;

            // // check if Cart has data, if yes then try to select the same branch.
            // if (this.cart.length > 0 && localStorage.getItem("cartFirm")) {
            //   this.firm = localStorage.getItem("cartFirm")
            //   let checkFromBranchList = this.firmList.find(firm => firm.id == this.firm)
            //   if (checkFromBranchList) {
            //     localStorage.setItem("selectedFirm", this.firm);
            //   } else {
            //     this.firm = viewData[0].id;
            //     localStorage.setItem("selectedFirm", this.firm);
            //     this.ProductStockNotAvailableForBranch()
            //   }
            // } else {
            //   this.firm = viewData[0].id;
            //   localStorage.setItem("selectedFirm", this.firm);
            // }

            // // By defoult branch box ki value set nahi ho rahi thi... kar ke dala
            // setTimeout(() => {
            //   this.firm = localStorage.getItem('selectedFirm');
            //   document.querySelector(".branchName").innerHTML = this.firmList.find(entry => entry.id == this.firm).name;

            //   this.getAvailableVarientList()
            // }, 500);

            // setTimeout(() => {
            //   this.tempFlag = true
            // }, 1500);

            // Extract all varient and group it by varient
            this.groupedByVariant = this.groupByKey(
              this.productDetails.pstock,
              'variant'
            );

            // Get All varient details from group
            let viewDataVarients: {
              name: string;
              item: any;
              enable: boolean;
            }[] = [];
            Object.keys(this.groupedByVariant).forEach((entry) => {
              viewDataVarients.push({
                name: entry,
                item: this.groupedByVariant[entry].length,
                enable: true,
              });
            });

            // Set Varients from data
            this.VarinetData = viewDataVarients;

            if (viewDataVarients) {
              this.variant = viewDataVarients[0].name;
              this.selectedStock = this.productDetails.pstock.find(
                (entry: { variant: any }) => entry.variant === this.variant
              );
              console.log(this.selectedStock);
            }

            // // Set all product
            this.product.id = response.id;
            this.product.img = this.docServer + '/' + response.image;
            this.product.name = response.name;
            this.product.prodtype = response.prodtype;
            this.product.rateunit = response.rateunit;
            this.product.brand = response.brand;
            this.product.mfg = response.brand;
            this.product.description = response.description;

            this.isPageLoaded = true;

            // // Updating stock at initial value
            // this.getStockbyIds()

            // this.isLoaded = true
          } else {
            // this.ProductStockUnAvailable()
            // this.cancel()
          }
        } else {
          // this.ProductStockNotAvailable()
          // this.cancel()
        }
      },
      (error) => {
        console.log('Error in data load.');
      }
    );
  }

  public groupByKey(data: any, key: any) {
    return data.reduce(function (current: any, entry: any) {
      (current[entry[key]] = current[entry[key]] || []).push(entry);
      return current;
    }, {});
  }

  public changeVarient(name: any) {
    this.variant = name;
    this.updateVarient();
  }

  public updateVarient() {
    let nowStock = this.productDetails.pstock.find(
      (entry: { variant: any }) => entry.variant === this.variant
    );
    if (nowStock) {
      this.selectedStock = nowStock;
    } else {
      // this.ProductStockNotAvailable();
    }
  }

  public alertVarientDisabled() {
    alert('Currently booking closed for this Item.');
  }

  public increaseQty() {
    this.qty++;
  }

  public decreaseQty() {
    this.qty--;
  }

  public addToCart() {
    this.route.navigate(['/login'])
    if (this.qty > 0) {
      this.product.fullloose = this.selectedStock.soldas;
      this.product.ratewithgst = this.selectedStock.ratewithgst;
      this.product.gst = this.selectedStock.gst;

      this.product.quantity = this.qty;
      this.product.price = this.selectedStock.rateperunit;
      this.product.gstamt = (this.product.gst * this.product.price) / 100;
      this.product.gstamt *= this.product.quantity;

      this.product.amount = this.product.price * this.product.quantity + this.product.gstamt;
      this.product.variant = this.variant;

      localStorage.setItem("selectedFirm", this.selectedStock.branch)

      console.log(this.selectedStock)

      this.cartService.addProduct(JSON.parse(JSON.stringify(this.product)));

      this.isGoToCartShow = true

      let msg = this.productDetail.name + " of " + this.variant + " is added to cart."

      Swal.fire({
        icon: 'success',
        title: 'Added to cart',
        text: msg,
        showConfirmButton: false,
        timer: 1500
      })
    }
  }

  public addToWishList() {
    if (this.qty > 0) {

      let myDate = new Date();
      let productTowish 

      if (this.VarinetData) {
        productTowish = {
          id: this.product.id,
          name: this.product.name,
          img: this.product.img,
          price: this.selectedStock.rateperunit,
          date: this.datePipe.transform(myDate, 'dd-MM-yyyy'),
          variant: this.variant,
        };
      } else {
        productTowish = {
          id: this.product.id,
          name: this.productDetail.name,
          img: this.docServer + "/" + this.productDetail.photos[0].docpath,
          price: "0",
          date: this.datePipe.transform(myDate, 'dd-MM-yyyy'),
          variant: "-",
        };
      }


      this.wishlist.addProduct(JSON.parse(JSON.stringify(productTowish)));

      let msg = this.productDetail.name + " of " + this.variant + " is added to Wishlist."

      Swal.fire({
        icon: 'success',
        title: 'Added to Wishlist',
        text: msg,
        showConfirmButton: false,
        timer: 1500
      })
    }
  }

  public submitQuery() {
    this.route.navigate(['/search',this.inputQuery])
  }
}
