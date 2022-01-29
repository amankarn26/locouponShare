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
  selector: 'ngx-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.scss']
})
export class AddCategoryComponent implements OnInit {

  public sendAbleData = {
    name: "",
    photo: {
      docname: "suv.jpg",
      docpath: "Category/ABC.jpg",
      docdesc: "category photo",
      doctype: "DOC"
    }
  }

  public fileSelection : any
  public docSelection : any

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

  constructor(private restService: AdminService, private toastrService: NbToastrService, private router: Router) { }

  ngOnInit(): void {
  }

  public submitForm() {
    if (this.validateData()) {
      if(this.uploadPhoto()) {
        const url = this.restService.categoryRestURL('add', "");
        this.restService.postFormData(url, this.sendAbleData).subscribe(
          (response: any) => {
            this.router.navigateByUrl('pages/category');
          },
          error => {
            console.log('Error in data load.');
            console.log(error)
            this.showToast(this.status, "Some Error Found!", "Please try after some time.");
          }
        );
      }
      else {
        this.showToast(this.status, "Image Not uploaded!", "Please try after some time.");
      }
    }
    else {
      this.showToast(this.status, this.title, this.content);
    }
  }

  private uploadPhoto() {
    if (this.fileSelection) {

      const rName = Math.random().toString(36).substring(7) + new Date().getTime();

      this.sendAbleData.photo.docname = this.fileSelection.name;
      this.sendAbleData.photo.doctype = 'CATEGORY';
      this.sendAbleData.photo.docpath = 'CATEGORY/' + rName + this.fileSelection.name;

      var docFormData = new FormData();
      docFormData.append('doc', this.fileSelection, rName + this.fileSelection.name);
      docFormData.append('width', '400');
      docFormData.append('height', '400');
      docFormData.append('name', 'CATEGORY/');
      var docUrl = this.restService.uploadRestURL('upload');
      this.restService.uploadFormData(docUrl, docFormData).subscribe(
        (response: any) => {
          console.log('Status on Upload API');
          console.log(response);
          return true;
        },
        error => {
          // Error Message
          console.log('Error in doc upload .');
          return false;
        }
      );
      return true;
    } else {
      return true;
    }
  }

  public fileEvent(fileInput: any) {
    // alert('file event begin');
    if (fileInput.target.files[0] != "") {
      this.fileSelection = fileInput.target.files[0];

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
    return true
  }

}
