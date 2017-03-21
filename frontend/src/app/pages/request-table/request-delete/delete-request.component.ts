import {Component, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";

@Component({
  selector: 'delete-request',
  templateUrl: 'delete-request.component.html'
})
export class DeleteRequestComponent {

  @Input()
  requests: Request[];

  @Output()
  updated: EventEmitter<any> = new EventEmitter();

  public request: Request;

  @ViewChild('deleteRequestFormModal')
  modal: ModalComponent;

  constructor(private requestService: RequestService,
              private toastr: ToastsManager) {
  }

  deleteRequest() {
    this.requestService.delete(this.request.id).subscribe(() => {
      this.updateArray();
      this.modal.close();
      this.toastr.success("Request was deleted successfully", "Success!");
    }, e => this.handleErrorDeleteUser(e));
  }

  private updateArray(): void {
    this.updated.emit(this.requests.filter(r => r !== this.request));
  }

  private handleErrorDeleteUser(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't delete request", 'Error');
    }
  }
}
