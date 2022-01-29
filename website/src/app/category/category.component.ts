import { DatePipe } from '@angular/common';
import { Component, OnChanges, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestService } from '../rest.service';
import { WishlistServiceService } from '../wishlist-service.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit,OnChanges {

  public prodTypeName:any
  public allProduct:any

  public docServer = this.restService.docServer

  constructor(
    private route: Router,
    public routprms: ActivatedRoute,
    private restService: RestService,
    private wishlist: WishlistServiceService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    window.scrollTo(0,0);

    this.prodTypeName = this.routprms.snapshot.params['name'];

    if (this.prodTypeName) {
      this.getSelectedProduct();
    }
    else {
      this.getAllProduct()
      this.prodTypeName = "Cars"
    }
    console.log("Working on init")

  }

  ngOnChanges(): void {
    console.log("Working on change")
  }

  private getAllProduct() {
    // Get Information from JSON REST api
    // const url = this.restService.catPageRest("getAllProduct", "");
    // this.restService.getJSONFromURL(url).subscribe(
    //   (response: any) => {
    //     this.allProduct = response;
    //   },
    //   (error) => {
    //     console.log("Error in data load.");
    //   }
    // ); 
    this.allProduct = this.restService.allProduct
  }
  
  private getSelectedProduct() {
    // Get Information from JSON REST api
    // const url = this.restService.catPageRest("getByProdType", this.prodTypeName);
    // this.restService.getJSONFromURL(url).subscribe(
    //   (response: any) => {
    //     this.allProduct = response;
    //   },
    //   (error) => {
    //     console.log("Error in data load.");
    //   }
    // );

    this.allProduct = this.restService.allProduct
  }

  public addToWishList(product:any) {
    let myDate = new Date();

    console.log(product);

    let productTowish = {
      id: product.id,
      name: product.name,
      img: this.docServer + '/' + product.path,
      price: "0",
      date: this.datePipe.transform(myDate, 'dd-MM-yyyy'),
      variant: "-",
    };


    this.wishlist.addProduct(JSON.parse(JSON.stringify(productTowish)));

    
  }
}
