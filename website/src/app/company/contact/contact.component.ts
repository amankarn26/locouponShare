import { Component, OnInit } from '@angular/core';
import { RestService } from 'src/app/rest.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {

  public contactEnquiry = {
    name: "",
    email: "",
    phone: "",
    message: ""
  }

  constructor(private restService: RestService) { }

  ngOnInit(): void {
    window.scrollTo(0, 0);
  }

  sendMessage() {
    if (this.contactEnquiry.name !== "" && this.contactEnquiry.email !== "" && this.contactEnquiry.phone !== "") {
      if (this.contactEnquiry.message !== "") {
        const url = this.restService.mailNode('sendEnquiry');
        this.restService.postFormData(url, this.contactEnquiry).subscribe(
          (response: any) => {
            Swal.fire({
              icon: 'success',
              title: 'Your Query submitted successfully.',
              text: 'Enjoy Shopping, we will contact you soon.',
              showConfirmButton: false,
              timer: 1500,
              willClose: () => {
                location.reload();
              },
            });
          },
          (error) => {
            // Error Message
            console.log('Error in data load.');
            Swal.fire('The Internet?', 'That thing is still around?', 'question');
          }
        );
        
      } else {
        Swal.fire({
          icon: 'warning',
          title: 'You missed message fields.',
          text: 'Please provide the details of query.',
          showConfirmButton: false,
          timer: 1500
        });
      }
    } else {
      Swal.fire({
        icon: 'warning',
        title: 'You missed some fields.',
        text: 'Please provide the details',
        showConfirmButton: false,
        timer: 1500
      });
    }
  }

}
