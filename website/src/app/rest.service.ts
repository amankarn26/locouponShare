import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  // public ip = "http://localhost:8090";
  public nodeIp = "https://demomail.amankarn.website"
  public ip = "https://aaitheantiquebake.com";
  // public ip = "https://cors-anywhere.herokuapp.com/http://45.32.62.106:8090";

  public server = this.ip + '/LoCouponManager';
  public docServer = this.ip + '/locoupondocs';

  constructor(private http: HttpClient) { }

  // Init Data (important)
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  httpOptions1 = {
    headers: new HttpHeaders({
      'Content-Type': 'form-data'
    })
  };

  public getJSONFromURL(url: any) {
    return this.http.get(url, { headers: new HttpHeaders({ timeout: `${30000}` }) });
  }

  public allProduct = [
    {
      id: 1,
      name: "Ford Bronco Sport",
      category: "SUV",
      rate: "$ 25,000",
      img: "assets/newImg/car1.png",
      description: "An SUV built for the thrill-seeker, the sightseer and the day-tripper. To enable your journey into the outdoors, this rugged build puts utility in the foreground with a purposeful design that includes available easy-to-clean surfaces and a ton of interior space thanks to the roomy architecture of the safari-style roof. * And with standard 4x4 capability, G.O.A.T. Modes™ (Goes Over Any Type of Terrain) and a HOSS suspension system, Bronco™ Sport is your gateway to the great outdoors."
    },
    {
      id: 2,
      name: "Ferrari F8",
      category: "SUV",
      rate: "$ 25,000",
      img: "assets/newImg/car2.png",
      description: "An SUV built for the thrill-seeker, the sightseer and the day-tripper. To enable your journey into the outdoors, this rugged build puts utility in the foreground with a purposeful design that includes available easy-to-clean surfaces and a ton of interior space thanks to the roomy architecture of the safari-style roof. * And with standard 4x4 capability, G.O.A.T. Modes™ (Goes Over Any Type of Terrain) and a HOSS suspension system, Bronco™ Sport is your gateway to the great outdoors."
    },
    {
      id: 3,
      name: "Land Rover Range Rover Velar",
      category: "SUV",
      rate: "$ 25,000",
      img: "assets/newImg/car3.png",
      description: "An SUV built for the thrill-seeker, the sightseer and the day-tripper. To enable your journey into the outdoors, this rugged build puts utility in the foreground with a purposeful design that includes available easy-to-clean surfaces and a ton of interior space thanks to the roomy architecture of the safari-style roof. * And with standard 4x4 capability, G.O.A.T. Modes™ (Goes Over Any Type of Terrain) and a HOSS suspension system, Bronco™ Sport is your gateway to the great outdoors."
    },
    {
      id: 4,
      name: "Ford Bronco Sport",
      category: "SUV",
      rate: "$ 25,000",
      img: "assets/newImg/car1.png",
      description: "An SUV built for the thrill-seeker, the sightseer and the day-tripper. To enable your journey into the outdoors, this rugged build puts utility in the foreground with a purposeful design that includes available easy-to-clean surfaces and a ton of interior space thanks to the roomy architecture of the safari-style roof. * And with standard 4x4 capability, G.O.A.T. Modes™ (Goes Over Any Type of Terrain) and a HOSS suspension system, Bronco™ Sport is your gateway to the great outdoors."
    },
    {
      id: 5,
      name: "Ferrari F8",
      category: "SUV",
      rate: "$ 25,000",
      img: "assets/newImg/car2.png",
      description: "An SUV built for the thrill-seeker, the sightseer and the day-tripper. To enable your journey into the outdoors, this rugged build puts utility in the foreground with a purposeful design that includes available easy-to-clean surfaces and a ton of interior space thanks to the roomy architecture of the safari-style roof. * And with standard 4x4 capability, G.O.A.T. Modes™ (Goes Over Any Type of Terrain) and a HOSS suspension system, Bronco™ Sport is your gateway to the great outdoors."
    },
    {
      id: 6,
      name: "Land Rover Range Rover Velar",
      category: "SUV",
      rate: "$ 25,000",
      img: "assets/newImg/car3.png",
      description: "An SUV built for the thrill-seeker, the sightseer and the day-tripper. To enable your journey into the outdoors, this rugged build puts utility in the foreground with a purposeful design that includes available easy-to-clean surfaces and a ton of interior space thanks to the roomy architecture of the safari-style roof. * And with standard 4x4 capability, G.O.A.T. Modes™ (Goes Over Any Type of Terrain) and a HOSS suspension system, Bronco™ Sport is your gateway to the great outdoors."
    },
  ]


  // Post Data
  public postFormData(url: any, json: any) {
    return this.http.post(url, json, this.httpOptions).pipe(
      map(res => {
        const postResponse = res;
        console.log('service method: ' + postResponse);
        return postResponse;
      })
    );
  }

  public uploadFormData(url: any, formData: any) {
    const headers = new Headers();
    return this.http.post(url, formData)
      .pipe(map(res => {
        const postResponse = res;
        console.log('service method: ' + postResponse);
        return postResponse;
      }));
  }



  public mailNode(restId: string) {
    let url = this.nodeIp
    switch (restId) {
      case "sendMail":
        url += '/send';
        break;
      case "sendEnquiry":
        url += '/sendEnquiry';
        break;

      default:
        url += '/send';
        break;
    }
    return url
  }

  public loginAllRest(restId: string, extra: string) {
    let url = this.server
    switch (restId) {
      case "check":
        url += '/login?email=' + extra;
        break;
      case "checkByType":
        url += '/login?type=' + extra;
        break;
      case "login":
        url += '/login';
        break;
      default:
        url += '/login';
        break;
    }
    return url
  }

  public customerRest(restId: string, extra: string) {
    let url = this.server
    switch (restId) {
      case "add":
        url += '/customer';
        break;
      case "all":
        url += '/customer';
        break;
      case "addId":
        url += '/customer/' + extra;
        break;
      case "update":
        url += '/customer/update';
        break;
      case "docs":
        url += '/customer/docs';
        break;

      default:
        url += '/customer';
        break;
    }
    return url
  }




  // Past URL
  public homePageRest(restid: string, stateId: any) {
    let url = this.server;
    switch (restid) {
      case 'banner':
        url += '/common/doc?type=OFFER';
        break;
      case 'hotdeals':
        url += '/offer/hotdeal';
        break;
      case 'prodtype':
        url += '/product/prodtype';
        break;
      case 'popularproducts':
        url += '/order/popular/product';
        break;
      case 'allproductype':
        url += '/product/producttype';
        break;
      default:
        url += '/offer/hotdeal/' + stateId;
        break;
    }
    return url;
  }

  public catPageRest(restid: string, stateId: any) {
    let url = this.server;
    switch (restid) {
      case 'getAllProduct':
        url += '/compact/product';
        break;
      case 'getByProdType':
        url += '/compact/product?prodtype=' + stateId;
        break;
      default:
        url += '/compact/product';
        break;
    }
    return url;
  }

  public productPageRest(restid: string, stateId: any) {
    let url = this.server;
    switch (restid) {
      case 'getAllProduct':
        url += '/product';
        break;
      case 'productbyid':
        url += '/product/' + stateId;
        break;
      case 'productbyidWithStock':
        url += '/compact/product?product=' + stateId;
        break;
      case 'popularproducts':
        url += '/order/popular/product';
        break;
      case 'allproductype':
        url += '/product/producttype';
        break;
      case 'getCompleteProductDetails':
        url += '/compact/product?product=' + stateId;
        break;
      default:
        url += '/offer/hotdeal/' + stateId;
        break;
    }
    return url;
  }

  public loginRest(restid: string, stateId: any) {
    let url = this.server;
    switch (restid) {
      case 'getState':
        url += '/common/pincode?pin=' + stateId;
        break;
      case 'addCutomer':
        url += '/customer';
        break;
      case 'login':
        url += '/login';
        break;
      case 'send':
        url += '/sms/send';
        break;
      case 'update':
        url += '/customer/update';
        break;
      case 'updatePass':
        url += '/login/update';
        break;
      default:
        url += '/customer';
        break;
    }
    return url;
  }

  public orderRest(restid: string, stateId: any) {
    let url = this.server;
    switch (restid) {
      case 'add':
        url += '/order';
        break;
      case 'getDeliveryCharge':
        url += '/dcharge' + stateId;
        break;
      default:
        url += '/order';
        break;
    }
    return url;
  }
}
