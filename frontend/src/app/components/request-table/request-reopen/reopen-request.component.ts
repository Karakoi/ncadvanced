import {Component, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {RequestService} from "../../../service/request.service";
import {ToastsManager} from "ng2-toastr";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../model/request.model";

@Component({
  selector: 'reopen-request',
  templateUrl: 'reopen-request.component.html',
  styleUrls: ['reopen-request.component.css']
})

export class ReopenRequestComponent {
  @Input()
  requests:Request[];
  @Output()
  updated:EventEmitter<any> = new EventEmitter();

  public request:Request;

  @ViewChild('reopenRequestFormModal')
  modal:ModalComponent;

  constructor(private requestService:RequestService,
              private toastr:ToastsManager) {
  }

  reopenRequest() {
    this.request.lastChanger = this.request.assignee;
    /*console.log(this.request);*/
    this.requestService.reopen(this.request).subscribe((item:Request) => {
      this.toastr.success("Request was reopened successfully", "Success!");
      this.updated.emit(this.requests.filter((request) => request.id !== item.id));
      this.modal.close();
    }, e => this.handleErrorReopenRequest(e));
  }


  private handleErrorReopenRequest(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't reopen this request", 'Error');
    }
  }
}

