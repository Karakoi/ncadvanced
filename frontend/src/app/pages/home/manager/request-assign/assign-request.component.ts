import {Component, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {RequestService} from "../../../../service/request.service";
import {ToastsManager} from "ng2-toastr";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../../model/request.model";
import {User} from "../../../../model/user.model";
import {AuthService} from "../../../../service/auth.service";

@Component({
  selector: 'assign-request',
  templateUrl: 'assign-request.component.html'
})

export class AssignRequestComponent {
  @Input()
  requests:Request[];
  @Output()
  updated:EventEmitter<any> = new EventEmitter();

  public request:Request;

  @ViewChild('assignRequestFormModal')
  modal:ModalComponent;

  constructor(private requestService:RequestService,
              private toastr:ToastsManager,
              private authService:AuthService) {
  }

  assignRequest() {
    this.authService.currentUser.subscribe((user:User) => {
      this.request.assignee = user;
      this.request.lastChanger = user;
      if (this.request.parentId === 0) {
        this.request.parentId = null;
      }
      this.request.estimateTimeInDays = this.request.estimateTimeInDays || 3;
      this.requestService.assign(this.request).subscribe(() => {
        this.toastr.success("Request was assigned successfully", "Success!");
        this.updated.emit(this.requests.filter(item => item["id"] !== this.request.id));
        this.modal.close();
      }, e => this.handleErrorAssignRequest(e));
    });
  }


  private handleErrorAssignRequest(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't assign request", 'Error');
    }
  }
}
