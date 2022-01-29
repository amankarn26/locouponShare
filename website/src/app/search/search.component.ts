import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RestService } from '../rest.service';
import { WishlistServiceService } from '../wishlist-service.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  public allProduct: any
  public searchQuery: any
  public displayQuery: any
  public searchResult: any

  private receivedQuery: any

  public docServer = this.restService.docServer

  // Flags
  public isSearch = false

  constructor(
    private restService: RestService,
    public routprms: ActivatedRoute,
    private wishlist: WishlistServiceService,
    private datePipe: DatePipe
  ) { }

  ngOnInit(): void {
    window.scrollTo(0, 0)


    // If params already there
    this.receivedQuery = this.routprms.snapshot.params['query'];
    this.searchQuery = this.receivedQuery
    this.getAllProduct()
  }

  private getAllProduct() {
    // Get Information from JSON REST api
    // const url = this.restService.productPageRest("getAllProduct", "");
    // this.restService.getJSONFromURL(url).subscribe(
    //   (response: any) => {
    //     this.allProduct = response;

    //     // If params already there
    //     if (this.receivedQuery) {

    //       this.searchQueryChange()
    //     }
    //   },
    //   (error) => {
    //     console.log("Error in data load.");
    //   }
    // );
    this.allProduct = this.restService.allProduct
    if (this.receivedQuery) {
      this.searchQueryChange()
    }
  }

  public searchQueryChange() {
    this.searchResult = this.allProduct.filter((entry: any) => {
      if (
        entry.name.toLowerCase().indexOf(this.searchQuery.toLowerCase()) > -1 
      ) {
        return entry
      }
    })
    this.isSearch = true
    this.displayQuery = this.searchQuery
    console.log(this.searchResult)
  }

  public addToWishList(product: any) {
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
