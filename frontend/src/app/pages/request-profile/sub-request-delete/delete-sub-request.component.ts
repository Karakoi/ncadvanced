import {Component, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";

@Component({
  selector: 'delete-sub-request',
  templateUrl: 'delete-sub-request.component.html'
})
export class DeleteSubRequestComponent {

  @Input()
  subRequests: Request[];

  @Output()
  updated: EventEmitter<any> = new EventEmitter();

  public subRequest: Request;

  @ViewChild('deleteSubRequestFormModal')
  modal: ModalComponent;

  constructor(private requestService: RequestService,
              private toastr: ToastsManager) {
  }

  deleteRequest() {
    this.requestService.delete(this.subRequest.id).subscribe(() => {
      this.updateArray();
      this.modal.close();
      this.toastr.success("Request was deleted successfully", "Success!");
    }, e => this.handleErrorDeleteUser(e));
  }

  private updateArray(): void {
    this.updated.emit(this.subRequests.filter(r => r !== this.subRequest));
  }

  private handleErrorDeleteUser(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't delete request", 'Error');
    }
  }
}
