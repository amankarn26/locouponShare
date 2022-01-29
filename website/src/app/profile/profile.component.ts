import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestService } from '../rest.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  public custName = localStorage.getItem('name')
  public title = "PROFILE"

  public currentSegment = 1

  // for profile

  public custPhone = localStorage.getItem('phone')
  public custEmail = localStorage.getItem('email')
  
  public custAddr: any

  public isAddrUpdated = false;
  public isPasswordUpdated = false;

  public password = {
    pass1 : "",
    pass2 : ""
  }

  public address = {
    line1: '',
    line2: '',
    city: '',
    pincode: '',
    states: '',
    latitude: '21.1491324',
    longitude: '79.050289',
    country: 'India',
  }

  constructor(
    private router: Router,
    private restService: RestService
  ) { }

  ngOnInit(): void {
    window.scrollTo(0,0);
    this.getAddr()

    console.log(this.router.url)
    if(this.router.url === "/order") this.changeSegment(2)
    else if(this.router.url === "/transection") this.changeSegment(3)
  }

  private getAddr() {

    let localAddr = localStorage.getItem('Address')
    if (localAddr) {
      this.custAddr = JSON.parse(localAddr)
      console.log(this.custAddr)
    }

  }

  public changeSegment(activeSegment: number) {
    this.currentSegment = activeSegment

    switch (activeSegment) {
      case 1:
        this.title = "PROFILE"
        break;
      case 2:
        this.title = "ORDERS"
        break;
      case 3:
        this.title = "TRANSECTIONS"
        break;
      case 4:
        this.title = "LOGOUT"
        break;
    
      default:
        break;
    }
  }

  public logout() {
    localStorage.clear()
    sessionStorage.clear()
    location.reload()
  }

  public logoutAndLogin() {
    // localStorage.clear()
    // sessionStorage.clear()
    this.router.navigateByUrl('/profile', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/login']);
    });
  }

  public getState() {
    const url = this.restService.loginRest(
      'getState',
      this.address.pincode
    );
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        this.address.states = response.state_name;
        this.address.city = response.city_name;
      },
      (error) => {
        console.log('Error in data load.');
      }
    );
  }

  public addressBtn() {
    this.isAddrUpdated = true;
    this.isPasswordUpdated = false;
  }

  public passwordBtn() {
    this.isAddrUpdated = false;
    this.isPasswordUpdated = true;
  }

  public closeAP() {
    this.isAddrUpdated = false;
    this.isPasswordUpdated = false;
  }

  public updateNow() {
    const jsono = {
      id: localStorage.getItem("id"),
      address: this.address,
    };
    console.log(jsono);
    const url = this.restService.loginRest("update", "");
    this.restService.postFormData(url, jsono).subscribe(
      (response: any) => {
        localStorage.setItem("Address", JSON.stringify(this.address));
        this.custAddr = this.address
        this.closeAP()
      },
      (error) => {
        console.log("Error in data load.");
        location.reload();
        // Doyour stuff here
      }
    );
  }

  public updateNowPass() {
    if (this.password.pass1 === this.password.pass2) {

      const jsono = {
        username: localStorage.getItem('phone'),
        password: window.btoa(this.password.pass1),
      };
  
      const url = this.restService.loginRest("updatePass", "");
      this.restService.postFormData(url, jsono).subscribe(
        (response: any) => {
          Swal.fire({
            icon: 'success',
            title: 'Password updated Successfully',
            text: 'Your Password update successfully',
            showConfirmButton: false,
            timer: 2500,
            willClose: () => {
              this.password.pass1 = ""
              this.password.pass2 = ""
              this.closeAP()
            },
          });
        },
        (error) => {
          console.log("Error in data load.");
          alert("Failed To Update");
          // Doyour stuff here
        }
      );
      
    } else {
      Swal.fire({
        icon: 'warning',
        title: 'Password Does Not Match',
        text: 'Please Reenter your new password',
        showConfirmButton: false,
        timer: 2500,
        willClose: () => {
          this.password.pass1 = ""
          this.password.pass2 = ""
        },
      });
    }
  }

}
