import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import {
  NbComponentStatus,
  NbGlobalLogicalPosition,
  NbGlobalPhysicalPosition,
  NbGlobalPosition,
  NbToastrService,
  NbToastrConfig,
  NbStepperComponent,
} from '@nebular/theme';
import { AdminService } from '../../../../services/admin.service';

@Component({
  selector: 'ngx-add-products',
  templateUrl: './add-products.component.html',
  styleUrls: ['./add-products.component.scss']
})
export class AddProductsComponent implements OnInit {

  @ViewChild('stepper') stepper: NbStepperComponent;

  public sendAbleData = {
    name: "",
    prodtype: "",
    details: "",
    actualprice: "",
    pointprice: "",
    status: "APPROVED",
    brand: ""
  }

  public allPhoto = {
    docname: "",
    docpath: "",
    docdesc: "",
    doctype: "",
    isprimary: "NO"
  }

  public sendablePic = []
  private saveInterval : any
  private photoUploaded = 0

  public filesToUpload1: any
  public filesToUpload2: any
  public filesToUpload3: any
  public filesToUpload4: any

  private allFiles = []

  public allProductType: []

  private registerUserId = 31
  public password = ""

  config: NbToastrConfig;

  index = 1;
  destroyByClick = true;
  duration = 5000;
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
    this.getCustomerList()
  }

  private getCustomerList() {
    this.restService.enableLoading();

    const url = this.restService.productTypeRestURL('getAll', "");
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        if (response) {
          this.allProductType = response
        } else {
          this.allProductType = []
        }
        this.restService.disableLoading();
      },
      error => {
        console.log('Error in data load.');
      }
    );
  }

  public moveNext() {
    this.router.navigateByUrl('pages/products');
  }

  public submitForm() {
    if (this.validateData()) {

      const url = this.restService.productRestURL('add', "");
      this.restService.postFormData(url, this.sendAbleData).subscribe(
        (response: any) => {
          // this.router.navigateByUrl('pages/employee');
          this.registerUserId = response.id
          this.stepper.next();
        },
        error => {
          console.log('Error in data load.');
          console.log(error)
        }
      );
    }
    else {
      console.log(this.sendAbleData);
      this.showToast(this.status, this.title, this.content);
    }
  }

  public fileEvent(fileInput: any, index: number) {
    if (fileInput.target.files[0] != "") {
      this.allFiles[index] = fileInput.target.files[0];

    }
  }

  public savePics() {
    if (this.allFiles[0]) {
      for (let index = 0; index < this.allFiles.length; index++) {
        const element = this.allFiles[index];
        if(!element) {
          this.showToast(this.status, "Some Photo missing", "Please upload 4 photo for the user.");
          return 0;
        }
      }
      this.uploadPhoto()
    } else {
      this.showToast(this.status, "Primary Photo not selected", "Please upload 4 photo for the user.");
    }

  }

  private uploadPhoto() {
    for (let index = 0; index < this.allFiles.length; index++) {
      const file = this.allFiles[index];
      if (file) {

        const rName = Math.random().toString(36).substring(7) + new Date().getTime();

        this.allPhoto.docname = file.name;
        this.allPhoto.doctype = "Product";
        this.allPhoto.docdesc = "Product Image"
        this.allPhoto.docpath = 'Product/' + this.registerUserId + rName + file.name;

        if (index === 0) {
          this.allPhoto.isprimary = "YES"
        } else {
          this.allPhoto.isprimary = "NO"
        }

        this.sendablePic.push(this.allPhoto)

        var docFormData = new FormData();
        docFormData.append('doc', file, rName + file.name);
        docFormData.append('width', '400');
        docFormData.append('height', '400');
        docFormData.append('name', 'Product/' + this.registerUserId);
        var docUrl = this.restService.uploadRestURL('upload');
        this.restService.uploadFormData(docUrl, docFormData).subscribe(
          (response: any) => {
            console.log('Status on Upload API');
            console.log(response);
            this.photoUploaded++
          },
          error => {
            // Error Message
            console.log('Error in doc upload .');
            this.photoUploaded++
          }
        );
      } 
    }
    this.checkAllPhotoUploaded()
  }

  private checkAllPhotoUploaded() {
    this.saveInterval = setInterval(() => {
      if (this.photoUploaded === this.allFiles.length) {
        this.updateCustomer()
      }
    },1000)
  }

  private updateCustomer() {
    clearInterval(this.saveInterval)
    const url = this.restService.productRestURL('uploadPhoto', "");
    let updateJson = {
      id: this.registerUserId,
      photos: this.sendablePic
    }
      this.restService.postFormData(url, updateJson).subscribe(
        (response: any) => {
          this.stepper.next();
        },
        error => {
          console.log('Error in data load.');
          console.log(error)
        }
      );
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
    if (this.sendAbleData.prodtype === "") {
      return false;
    }
    if (this.sendAbleData.details === "") {
      return false;
    }
    if (this.sendAbleData.actualprice === "") {
      return false;
    }
    if (this.sendAbleData.pointprice === "") {
      return false;
    }
    if (this.sendAbleData.status === "") {
      return false;
    }
    if (this.sendAbleData.brand === "") {
      return false;
    }
    return true
  }


}
