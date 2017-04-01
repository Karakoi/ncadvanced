import {Component, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";

@Component({
  selector: 'close',
  templateUrl: 'close.component.html'
})
export class CloseComponent {
  @Output()
  updated: EventEmitter<any> = new EventEmitter();

  public request: Request;

  @ViewChild('closeFormModal')
  modal: ModalComponent;

  constructor(private requestService: RequestService,
              private toastr: ToastsManager) {
  }

  closed() {
    this.requestService.close(this.request).subscribe((item) => {
      this.return(item);
      this.modal.close();
      this.toastr.success("Request was closed successfully", "Success!");
    }, e => this.handleErrorDeleteUser(e));
  }

  private return(request:Request): void {
    this.updated.emit(request);
  }

  private handleErrorDeleteUser(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't close request", 'Error');
    }
  }
}

