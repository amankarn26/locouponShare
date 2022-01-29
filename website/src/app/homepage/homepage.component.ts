import { DatePipe } from '@angular/common';
import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestService } from '../rest.service';
import { WishlistServiceService } from '../wishlist-service.service';
import Swal from 'sweetalert2'

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  public innerWidth: any;
  public selectedScreen: any;

  // All Variables
  public hotdeals:any;
  public hotdealsSlice:any;
  public hotdealsLoaded:boolean = false;

  public prodType:any;
  public prodTypeLoaded:boolean = false;

  public topProduct:any;

  public producttype:any;

  public offerList:any;


  public docServer = this.restService.docServer


  public slideIndex = 1;

  public isVisible = false

  public inputQuery: any;



  images = [
    {path: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'},
    {path: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'},
    {path: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'},
    {path: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'},
    {path: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'},
    {path: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'},
    {path: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'},
    {path: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'},
    {path: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'},
  ]


  constructor(
    private route: Router,
    private restService: RestService,
    private wishlist: WishlistServiceService,
    private datePipe: DatePipe
  ) { }

  ngOnInit(): void {

    this.innerWidth = window.innerWidth;
    this.setSelectedScreen()

    this.getOffers()
    this.showSlides(1)
    this.firstImage()
    this.getPopularProducts()
    this.getProductType()
    this.getTopProduct()
    this.getAllProductType()
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.innerWidth = window.innerWidth;
    this.setSelectedScreen()
  }

  public setSelectedScreen() {
    if (this.innerWidth > 1024) {
      this.selectedScreen = 1
    } else if (this.innerWidth > 769) {
      this.selectedScreen = 2
    } else {
      this.selectedScreen = 3
    }
  }

  public getDetailsProduct(getProdID:number) {
    this.route.navigate(['/product',getProdID]);
    // alert("Server Error !");
  }

  public getCategory(getcatID:number) {
    // this.route.navigate(['/catagory',getcatID]);
    this.route.navigate(['/catagory']);
    // alert("Server Error !");
  }

  public plusSlides(n:number) {
    this.showSlides(this.slideIndex += n);
  }

  public currentSlide(n:number) {
    this.showSlides(this.slideIndex = n);
  }

  public showSlides(n:number) {
    var i;
    if (n > 3) { this.slideIndex = 1 }
    else if (n < 1) { this.slideIndex = 3 }
    else { this.slideIndex = n}
  }

  public firstImage() {
    setInterval(() => {
      this.plusSlides(1)
    }, 7000)
  }

  private getOffers() {
    // Get Information from JSON REST api
    const url = this.restService.homePageRest("banner", "");
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        this.offerList = response;

        console.log("getPopularProducts ka data " + url);
        console.log(response);
      },
      (error) => {
        console.log("Error in data load.");
      }
    );
  }

  private getPopularProducts() {
    // Get Information from JSON REST api
    const url = this.restService.homePageRest("hotdeals", "");
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        this.hotdeals = response;
        this.hotdealsSlice = response.slice(1,this.hotdeals.length);
        this.hotdealsLoaded = true
      },
      (error) => {
        console.log("Error in data load.");
      }
    );
  }
  private getProductType() {
    // Get Information from JSON REST api
    this.prodType = []
  }
  public getTopProduct(){
    // Get Information from JSON REST api
    const url = this.restService.homePageRest('popularproducts', '');
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        this.topProduct = response;
      },
      error => {
        console.log('Error in data load.');
      }
    );
  }

  private getAllProductType() {
    const url = this.restService.homePageRest("allproductype", "");
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        this.producttype = response;
      },
      (error) => {
        console.log("Error in data load.");
      }
    );
  }

  cards = [
    {
      title: 'Good customer service ',
      description: 'Theme has nice and rich features, also good design. This is our first time with Developer - ThemeMove and so far everything is great.',
      person: 'Emily Noth ',
      postion: '/ Agency, Germany ',
      img: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'
    },
    {
      title: 'Love this ',
      description: 'Pretty much every single pixel can easily be customized. Great theme!',
      person: 'Karoline Smith ',
      postion: '/ Video Producer, Italy',
      img: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'
    },
    {
      title: 'Great quality!',
      description: 'This is a great design and I hope that we will create a website with a good signature. ThemeMove team is reactive and kind. Thanks for the help so far.',
      person: 'Luke Olfert ',
      postion: '/ Web Developer, Canada',
      img: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'
    },
    {
      title: 'My luck',
      description: 'Before my purchase, I had a look of many other themes & clubs that are available out there. So, ThemeMove is just good luck for me. 5-star rating deserved.',
      person: 'Ryan Teixeira',
      postion: '/ Designer, UK',
      img: 'https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20(34).jpg'
    },
  ];

  public addToWishList(product:any) {
    let myDate = new Date();

    console.log(product);

    let productTowish = {
      id: product.id,
      name: product.name,
      img: this.docServer + '/' + product.photos[0].docpath,
      price: "0",
      date: this.datePipe.transform(myDate, 'dd-MM-yyyy'),
      variant: "-",
    };


    this.wishlist.addProduct(JSON.parse(JSON.stringify(productTowish)));

    let msg = product.name + " is added to Wishlist."

    Swal.fire({
      icon: 'success',
      title: 'Added to Wishlist',
      text: msg,
      showConfirmButton: false,
      timer: 1500
    })
  }

  public submitQuery() {
    this.route.navigate(['/search',this.inputQuery])
  }
}
