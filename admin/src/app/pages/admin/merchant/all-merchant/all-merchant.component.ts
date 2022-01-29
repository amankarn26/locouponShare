import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalDataSource } from 'ng2-smart-table';
import { SmartTableData } from '../../../../@core/data/smart-table';
import { AdminService } from '../../../../services/admin.service';

@Component({
  selector: 'ngx-all-merchant',
  templateUrl: './all-merchant.component.html',
  styleUrls: ['./all-merchant.component.scss']
})
export class AllMerchantComponent implements OnInit {

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
        title: 'ID',
        type: 'string',
      },
      name: {
        title: 'Name',
        type: 'string',
      },
      phone: {
        title: 'Phone Number',
        type: 'string',
      },
      email: {
        title: 'E-mail',
        type: 'string',
      }
    },
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(private service: SmartTableData, private restService: AdminService, private router: Router) {
    const data = this.service.getData();
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

    const url = this.restService.merchantRestURL('getAll', "");
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

  public editUser(event):void {
    console.log(event);
  }

  public addMerchant():void {
    this.router.navigateByUrl('pages/addMerchant');
  }

  public deleteMerchant(event):void {
    const url = this.restService.merchantRestURL('byId', event.data.id);
    this.restService.deleteFormData(url).subscribe(
      (response: any) => {
        this.getCustomerList()
        this.restService.disableLoading();
      },
      error => {
        console.log('Error in data load.');
      }
    );
  }

}
