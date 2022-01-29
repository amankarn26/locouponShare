import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalDataSource } from 'ng2-smart-table';
import Swal from 'sweetalert2';
import { SmartTableData } from '../../../../@core/data/smart-table';
import { AdminService } from '../../../../services/admin.service';

@Component({
  selector: 'ngx-all-category',
  templateUrl: './all-category.component.html',
  styleUrls: ['./all-category.component.scss']
})
export class AllCategoryComponent implements OnInit {

  settings = {
    add: {
      addButtonContent: '<i class="nb-plus"></i>',
      createButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    edit: {
      editButtonContent: '<i class="nb-compose"></i>',
      saveButtonContent: '<i class="nb-checkmark"></i>',
      cancelButtonContent: '<i class="nb-close"></i>',
    },
    delete: {
      deleteButtonContent: '<i class="nb-trash"></i>',
      confirmDelete: true,
    },
    mode: 'external',
    columns: {
      id: {
        title: 'Name',
        type: 'string',
      },
      name: {
        title: 'Name',
        type: 'string',
      },
      image: {
        title: 'Image',
        type: 'html',
        width: "20px"
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(private service: SmartTableData, private restService: AdminService, private router: Router) {
    const data = this.service.getData();
    // this.source.load(data);
    this.getCustomerList()
  }

  ngOnInit(): void {
  }

  onDeleteConfirm(event): void {
    if (window.confirm('Are you sure you want to delete?')) {
      event.confirm.resolve();
    } else {
      event.confirm.reject();
    }
  }

  private getCustomerList() {
    this.restService.enableLoading();

    const url = this.restService.categoryRestURL('getAll', "");
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        response = response.map(element => {
          element.image = '<a href="' + this.restService.imgServer + "/" + element.photo.docpath + '" target="_blank" download >'+element.photo.docname+'</a>'
          return element
        })
        this.source.load(response);
        this.restService.disableLoading();
      },
      error => {
        console.log('Error in data load.');
      }
    );
  }

  public editCategory(event):void {
    console.log(event);
  }

  public addCategory():void {
    this.router.navigateByUrl('pages/addCategory');
  }

  public deleteCategory(event):void {
    const url = this.restService.categoryRestURL('byId', event.data.id);
    this.restService.deleteFormData(url).subscribe(
      (response: any) => {
        this.getCustomerList()
        this.restService.disableLoading();
      },
      error => {
        console.log('Error in data load.');
        Swal.fire(
          'Category is in Use!',
          'We can not delete this Category.',
          'error'
        )
      }
    );
  }

}
