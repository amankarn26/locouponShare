import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalDataSource } from 'ng2-smart-table';
import Swal from 'sweetalert2';
import { SmartTableData } from '../../../../@core/data/smart-table';
import { AdminService } from '../../../../services/admin.service';

@Component({
  selector: 'ngx-all-products',
  templateUrl: './all-products.component.html',
  styleUrls: ['./all-products.component.scss']
})
export class AllProductsComponent implements OnInit {

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
        title: 'Product ID',
        type: 'string',
      },
      name: {
        title: 'Name',
        type: 'string',
      },
      prodtype: {
        title: 'Product Type',
        type: 'string',
      },
      brand: {
        title: 'Brand',
        type: 'string',
      },
      status: {
        title: 'Status',
        type: 'string',
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

    const url = this.restService.productRestURL('getAll', "");
    this.restService.getJSONFromURL(url).subscribe(
      (response: any) => {
        this.source.load(response);
        this.restService.disableLoading();
      },
      error => {
        console.log('Error in data load.');
      }
    );
  }

  public editUser(event): void {
    console.log(event);
  }

  public addProduct(): void {
    this.router.navigateByUrl('pages/addProducts');
  }

  public deleteProduct(event): void {
    Swal.fire({
      title: 'Do you want to delete this product?',
      showCancelButton: true,
      confirmButtonText: 'Yes',
    }).then((result) => {
      if (result.isConfirmed) {
        const url = this.restService.productRestURL('byId', event.data.id);
        this.restService.deleteFormData(url).subscribe(
          (response: any) => {
            Swal.fire('Saved!', '', 'success')
            this.getCustomerList()
            this.restService.disableLoading();
          },
          error => {
            console.log('Error in data load.');
          }
        );
        
      } else if (result.isDenied) {
        Swal.fire('Data is safe', '', 'info')
      }
    })

  }

}
