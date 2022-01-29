import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  NbComponentStatus,
  NbGlobalLogicalPosition,
  NbGlobalPhysicalPosition,
  NbGlobalPosition,
  NbToastrService,
  NbToastrConfig,
} from '@nebular/theme';
import { AdminService } from '../../../../services/admin.service';

@Component({
  selector: 'ngx-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss']
})
export class AddEmployeeComponent implements OnInit {

  public sendAbleData = {
    name: "",
    dob: "1999-09-02",
    phone: "",
    email: "",
    login: {
      type: "EMPLOYEE",
      email: "",
      password: ""
    },
    city: "",
    gender: "FEMALE",
    address: {
      line1: "",
      line2: "",
      city: "",
      pincode: "",
      states: "",
      country: "India"
    }
  }

  config: NbToastrConfig;

  index = 1;
  destroyByClick = true;
  duration = 2000;
  hasIcon = true;
  position: NbGlobalPosition = NbGlobalPhysicalPosition.TOP_RIGHT;
  preventDuplicates = false;
  status: NbComponentStatus = 'danger';

  title = 'You Missed Something!';
  content = `Please Fill all details to proceed`;

  types: NbComponentStatus[] = [
    'primary',
    'success',
    'info',
    'warning',
    'danger',
  ];
  positions: string[] = [
    NbGlobalPhysicalPosition.TOP_RIGHT,
    NbGlobalPhysicalPosition.TOP_LEFT,
    NbGlobalPhysicalPosition.BOTTOM_LEFT,
    NbGlobalPhysicalPosition.BOTTOM_RIGHT,
    NbGlobalLogicalPosition.TOP_END,
    NbGlobalLogicalPosition.TOP_START,
    NbGlobalLogicalPosition.BOTTOM_END,
    NbGlobalLogicalPosition.BOTTOM_START,
  ];

  quotes = [
    { title: null, body: 'We rock at Angular' },
    { title: null, body: 'Titles are not always needed' },
    { title: null, body: 'Toastr rock!' },
  ];


  constructor(private restService: AdminService, private toastrService: NbToastrService, private router: Router) { }

  ngOnInit(): void {
  }

  public submitForm() {
    if (this.validateData()) {
      this.sendAbleData.city = this.sendAbleData.address.city
      this.sendAbleData.login.email = this.sendAbleData.email
      const url = this.restService.employeeRestURL('add', "");
      this.restService.postFormData(url, this.sendAbleData).subscribe(
        (response: any) => {
          this.router.navigateByUrl('pages/employee');
        },
        error => {
          console.log('Error in data load.');
          console.log(error)
        }
      );
    }
    else {
      this.showToast(this.status, this.title, this.content);
    }
  }

  private showToast(type: NbComponentStatus, title: string, body: string) {
    const config = {
      status: type,
      destroyByClick: this.destroyByClick,
      duration: this.duration,
      hasIcon: this.hasIcon,
      position: this.position,
      preventDuplicates: this.preventDuplicates,
    };
    const titleContent = title ? `${title}` : '';

    this.index += 1;
    this.toastrService.show(
      body,
      titleContent,
      config);
  }

  private validateData() {
    if (this.sendAbleData.name === "") {
      return false;
    }
    if (this.sendAbleData.dob === "") {
      return false;
    }
    if (this.sendAbleData.phone === "") {
      return false;
    }
    if (this.sendAbleData.email === "") {
      return false;
    }
    if (this.sendAbleData.gender === "") {
      return false;
    }
    if (this.sendAbleData.login.password === "") {
      return false;
    }
    if (this.sendAbleData.address.line1 === "") {
      return false;
    }
    if (this.sendAbleData.address.line2 === "") {
      return false;
    }
    if (this.sendAbleData.address.pincode === "") {
      return false;
    }
    if (this.sendAbleData.address.states === "") {
      return false;
    }
    return true
  }

}
