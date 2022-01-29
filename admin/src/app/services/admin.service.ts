import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { Bug } from './bug';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import Swal from 'sweetalert2'
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  // public ip = "https://aaitheantiquebake.com"
  public ip = "http://localhost:8090"

  public server = this.ip + '/LoCouponManager';
  public docServer = this.ip + '/locoupondocs';
  public imgServer = this.ip + '/locoupondocs';

  constructor(private http: HttpClient) { }

  // Http Headers
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  // Http Headers
  httpOptions1 = {
    headers: new HttpHeaders({
      'Content-Type': 'form-data'
    })
  };

  public enableLoading() {
    setTimeout(() => {
      Swal.fire('Hold On ! ..', 'We are Retrieving your data');
      Swal.showLoading();
    }, 500);
  }

  public disableLoading() {
    setTimeout(() => { Swal.close(); }, 500); // 5000
  }

  public serverError() {
    Swal.fire('Cannot get Response from Server !', '', 'info');
  }

  // Get Data
  public getJSONFromURL(url) {
    return this.http.get(url); // .map((response:any) => response.json());


  }

  // Post Data
  public postFormData(url, json) {
    return this.http.post(url, json, this.httpOptions).pipe(
      map(res => {
        const postresponse = res;
        console.log('service method: ' + postresponse);
        return postresponse;
      })
    );
  }

  public uploadFormData(url, formdata) {
    return this.http.post(url, formdata)
      .pipe(map(res => {
        const postresponse = res;
        console.log('service method: ' + postresponse);
        return postresponse;
      }));
  }

  // Delete Data
  public deleteFormData(url) {
    return this.http.delete(url).pipe(
      map((res: Response) => {
        const deleteresponse = res; // .json();
        console.log('service method: ');
        console.log(deleteresponse);
        console.log('end delete response in rest');

        return deleteresponse;
      })
    );
  }

  public uploadRestURL(restId) {
    var url = this.server;
    switch (restId) {
      case 'upload':
        url += '/common/doc/upload';
        break;

      default:
        url += '/common/doc/upload';
        break;
    }
    return url;
  }


  public customerRestURL(restId, userId) {
    var url = this.server;
    switch (restId) {

      case 'add':
        url += '/customer';
        break;

      case 'getAll':
        url += '/customer';
        break;

      case 'edit':
        url += '/customer/update';
        break;

      case 'byId':
        url += '/customer/' + userId;
        break;

      default:
        url += '/customer';
        break;
    }
    return url;
  }

  public employeeRestURL(restId, userId) {
    var url = this.server;
    switch (restId) {

      case 'add':
        url += '/employee';
        break;

      case 'getAll':
        url += '/employee';
        break;

      case 'edit':
        url += '/employee/update';
        break;

      case 'byId':
        url += '/employee/' + userId;
        break;

      default:
        url += '/employee';
        break;
    }
    return url;
  }

  public merchantRestURL(restId, userId) {
    var url = this.server;
    switch (restId) {

      case 'add':
        url += '/merchant';
        break;

      case 'getAll':
        url += '/merchant';
        break;

      case 'edit':
        url += '/merchant/update';
        break;

      case 'byId':
        url += '/merchant/' + userId;
        break;

      default:
        url += '/merchant';
        break;
    }
    return url;
  }

  public categoryRestURL(restId, userId) {
    var url = this.server;
    switch (restId) {
      case 'add':
        url += '/category';
        break;

      case 'getAll':
        url += '/category';
        break;

      case 'edit':
        url += '/category/update';
        break;

      case 'byId':
        url += '/category/' + userId;
        break;

      default:
        url += '/category';
        break;
    }
    return url;
  }

  public productTypeRestURL(restId, userId) {
    var url = this.server;
    switch (restId) {
      case 'add':
        url += '/product/prodtype';
        break;

      case 'getAll':
        url += '/product/prodtype';
        break;

      case 'edit':
        url += '/product/prodtype/update';
        break;

      case 'byId':
        url += '/product/prodtype/' + userId;
        break;

      default:
        url += '/product/prodtype';
        break;
    }
    return url;
  }

  public productRestURL(restId, userId) {
    var url = this.server;
    switch (restId) {
      case 'add':
        url += '/product';
        break;

      case 'getAll':
        url += '/product';
        break;

      case 'edit':
        url += '/product/update';
        break;

      case 'byId':
        url += '/product/' + userId;
        break;

      case 'uploadPhoto':
        url += '/product/photos';
        break;

      default:
        url += '/product';
        break;
    }
    return url;
  }
}
