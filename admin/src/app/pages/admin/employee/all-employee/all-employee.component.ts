import { Component, OnInit } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';
import { SmartTableData } from '../../../../@core/data/smart-table';
import { AdminService } from '../../../../services/admin.service';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-all-employee',
  templateUrl: './all-employee.component.html',
  styleUrls: ['./all-employee.component.scss']
})
export class AllEmployeeComponent implements OnInit {

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
      },
    },
  };

  source: LocalDataSource = new LocalDataSource();

  constructor(private service: SmartTableData, private restService: AdminService, private router: Router) {
    const data = this.service.getData();
    // this.source.load(data);
    this.getEmployeeList()
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

  private getEmployeeList() {
    this.restService.enableLoading();

    const url = this.restService.employeeRestURL('getAll', "");
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

  public addUser():void {
    this.router.navigateByUrl('pages/addEmployee');
  }

  public deleteEmployee(event):void {
    const url = this.restService.employeeRestURL('byId', event.data.id);
    this.restService.deleteFormData(url).subscribe(
      (response: any) => {
        this.getEmployeeList()
        this.restService.disableLoading();
      },
      error => {
        console.log('Error in data load.');
      }
    );
  }

}
