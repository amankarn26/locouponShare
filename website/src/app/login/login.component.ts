import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  user = {
    phone: '',
    name: '',
    // gender:'',
    // dob:'1999-09-02',
    email: '',
    city: '',
    pan: '',
    aadhaar: "",
    address: {
      line1: '',
      line2: '',
      city: '',
      pincode: '',
      states: '',
      latitude: '21.1491324',
      longitude: '79.050289',
      country: 'India',
    },
    login: {
      type: 'CUSTOMER',
      username: '', // ----------User name can be phone or email
      password: '',
      role: 'CUSTOMER',
    },
  };

  login = {
    username: '',
    password: '',
  };

  private otp: any;
  public otpBtn = 'Login with OTP';
  public isOtp = false;

  constructor(private restService: RestService) { }

  ngOnInit(): void {
    window.scrollTo(0, 0);
  }

  getState() {
    const url = "https://aaitheantiquebake.com/MoleculeManager/common/pincode?pin=" + this.user.address.pincode
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        // this.restService.getJSONFromURL(response);
        // this.statelist = response;
        this.user.address.states = response.state_name;
        this.user.address.city = response.city_name;
      },
      (error) => {
        console.log('Error in data load.');
      }
    );
  }

  public register() {
    // send the mail
    this.sendMailOfRegistration();

    // Submit data to database
    let sendableData = {
      name: this.user.name,
      dob: "1999-09-02",
      phone: this.user.phone,
      email: this.user.email,
      login: {
        type: "CUSTOMER",
        email: this.user.email,
        password: this.user.login.password
      },
      city: this.user.address.city,
      gender: "FEMALE",
      aadhar: this.user.aadhaar,
      pan: this.user.pan,
      address: {
        line1: this.user.address.line1,
        line2: this.user.address.line2,
        city: this.user.address.city,
        pincode: this.user.address.pincode,
        states: this.user.address.states,
        country: this.user.address.country
      }
    }
    const url = this.restService.customerRest('add', "");
    this.restService.postFormData(url, sendableData).subscribe(
      (response: any) => {
        // Responce data.
        console.log('Status on Post API');
        console.log(response);
        window.scrollTo(0, 0);

        Swal.fire({
          icon: 'success',
          title: 'You Registered successfully',
          text: 'Enjoy Shopping, Soon we will get in touch with you.',
          showConfirmButton: false,
          timer: 4500,
          willClose: () => {
            location.reload();
          },
        });
      },
      (error) => {
        // Error Message
        console.log('Error in data load.');
        console.log(error);
        if (error.error === "DUPLICATE_CUSTOMER") {
          Swal.fire('Already Registered', 'Please wait until we contact you!', 'error');
        } else {
          Swal.fire('The Internet?', 'That thing is still around?', 'question');
        }
        
      }
    );
  }
  private sendMailOfRegistration() {
    this.user.login.username = this.user.phone;
    this.user.login.password = window.btoa(this.user.login.password);
    const url = this.restService.mailNode('sendMail');
    this.restService.postFormData(url, this.user).subscribe(
      (response: any) => {
        // Responce data.
        console.log('Status on Post API');
        console.log(response);

      },
      (error) => {
        // Error Message
        console.log('Error in data load.');
        Swal.fire('Unable to send mail!', 'Please try again after some time.', 'error');
      }
    );
  }

  public checkEmail() {
    const url = this.restService.loginAllRest('check', this.user.email);
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        if (response.error !== "NONE") {
          Swal.fire('Already Register?', 'Please dont register yourself again.', 'error');
        }
      },
      (error) => {
        console.log('Error in data load.');
      }
    );
  }

  public loginNow() {
    if (this.login.username != '' && this.login.password != '') {
      console.log(this.login);
      const url = this.restService.loginRest('login', '');
      this.login.password = window.btoa(this.login.password); // added on 5/1/2019
      this.restService.postFormData(url, this.login).subscribe(
        (response: any) => {
          if (response.login.role == 'CUSTOMER') {
            localStorage.setItem('id', response.id);
            localStorage.setItem('name', response.name);
            localStorage.setItem('role', response.login.role);
            localStorage.setItem('type', response.login.type);
            localStorage.setItem('islogin', 'true');
            localStorage.setItem('email', response.email);
            localStorage.setItem('phone', response.phone);
            localStorage.setItem('Address', JSON.stringify(response.address));
            localStorage.setItem('pas', response.login.password);

            if (response.photo) {
              sessionStorage.setItem('photo', response.photo.docpath);
            }
            if (response.phone) {
              sessionStorage.setItem('phone', response.phone);
            }

            Swal.fire({
              icon: 'success',
              title: 'You Logged in successfully',
              text: 'Enjoy Shopping',
              showConfirmButton: false,
              timer: 1500,
              willClose: () => {
                location.reload();
              },
            });
          } else {
            Swal.fire({
              icon: 'info',
              title: 'Invalid account',
              text: 'Please use customer account Only',
              showConfirmButton: false,
              timer: 1500,
            });
          }
        },
        (error) => {
          // Error Message

          console.log('Error in data load.');

          Swal.fire({
            icon: 'error',
            title: 'Invalid Credientals',
            text: 'Please check your number and password again!',
            showConfirmButton: false,
            timer: 2500,
          });
        }
      );

      //  location.reload();
    } else {
      console.log('Please provide user ID and Password.');
      Swal.fire({
        icon: 'error',
        title: 'Invalid Credientals',
        text: 'Please fill your number and password!',
        showConfirmButton: false,
        timer: 1500,
      });
    }
  }

  public loginNowOtp() {
    if (Number(this.otp) == Number(this.login.password)) {
      let sendableData = {
        username: this.login.username
      }

      if (this.login.username !== '') {
        // login code start**************************************************************************************
        console.log('submitData function');
        console.log(this.login);
        const url = this.restService.loginRest('login', '');
        this.restService.postFormData(url, sendableData).subscribe(
          (response: any) => {
            if (response.login.role == 'CUSTOMER') {
              localStorage.setItem('id', response.id);
              localStorage.setItem('name', response.name);
              localStorage.setItem('role', response.login.role);
              localStorage.setItem('type', response.login.type);
              localStorage.setItem('islogin', 'true');
              localStorage.setItem('email', response.email);
              localStorage.setItem('phone', response.phone);
              localStorage.setItem('Address', JSON.stringify(response.address));
              localStorage.setItem('pas', response.login.password);

              if (response.photo) {
                sessionStorage.setItem('photo', response.photo.docpath);
              }
              if (response.phone) {
                sessionStorage.setItem('phone', response.phone);
              }

              Swal.fire({
                icon: 'success',
                title: 'You Logged in successfully',
                text: 'Enjoy Shopping',
                showConfirmButton: false,
                timer: 1500,
                willClose: () => {
                  location.reload();
                },
              });
            }
          },
          (error) => {
            console.log('Error in data load.');
          }
        );
      }
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Invalid OTP',
        text: 'Please check your OTP again!',
        showConfirmButton: false,
        timer: 1500,
      });
    }


  }

  public getOTP() {
    if (this.isOtp) {
      this.isOtp = false;
      this.otpBtn = 'Login with OTP';
      this.login.password = ""
    } else {
      this.login.password = ""
      if (this.login.username) {
        // Generate OTP
        this.otp = String(Math.floor(100000 + Math.random() * 900000));

        const message = 'Your OTP for verification is : ' + this.otp + '. IVPL';

        const mobile = this.login.username;
        const smsdata = {
          toMobile: mobile,
          message: message,
          tempid: '1007144669346303839',
        };

        console.log('sending sms');
        console.log(smsdata);
        const url = this.restService.loginRest('send', '');

        console.log(url);
        this.restService.postFormData(url, smsdata).subscribe(
          (response) => {
            // Responce data.

            this.isOtp = true;
            this.otpBtn = 'Login with Password';
          },
          (error) => {
            console.log('Error in SMS.');
          }
        );
      } else {
        Swal.fire({
          icon: 'error',
          title: 'Invalid Credientals',
          text: 'Please check your Phone number!',
          showConfirmButton: false,
          timer: 2500,
        });
      }
    }
  }
}
