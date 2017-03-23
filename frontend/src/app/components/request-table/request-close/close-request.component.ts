import {Component, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {RequestService} from "../../../service/request.service";
import {ToastsManager} from "ng2-toastr";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../model/request.model";

@Component({
  selector: 'close-request',
  templateUrl: 'close-request.component.html',
  styleUrls: ['close-request.component.css']
})

export class CloseRequestComponent {
  @Input()
  requests:Request[];
  @Output()
  updated:EventEmitter<any> = new EventEmitter();

  public request:Request;

  @ViewChild('closeRequestFormModal')
  modal:ModalComponent;

  constructor(private requestService:RequestService,
              private toastr:ToastsManager) {
  }

  closeRequest() {
    this.requestService.close(this.request).subscribe((item: Request) => {
      this.toastr.success("Request was closed successfully", "Success!");
      this.updated.emit(this.requests.filter((request) => request.id !== item.id));
      this.modal.close();
    }, e => this.handleErrorCloseRequest(e));
  }


  private handleErrorCloseRequest(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't close this request", 'Error');
    }
  }
}

