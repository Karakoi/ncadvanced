import {Component, ViewChild} from "@angular/core";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";
import {Role} from "../../../model/role.model";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'request-details',
  templateUrl: 'request-details.component.html',
  styleUrls: ['request-details.component.css']
})
export class RequestDetailsComponent {
  showDescription: boolean = true;
  showHistory: boolean = true;
  public request: Request;
  role: Role;

  @ViewChild('requestDetailsModal')
  modal: ModalComponent;

  constructor(private requestService: RequestService,
              private toastr: ToastsManager) {
    this.role = {
      id: 10,
      name: "admin"
    };
  }

  changeShowDescription() {
    this.showDescription = !this.showDescription;
  }

  changeShowHistory() {
    this.showHistory = !this.showHistory;
  }

  updateRequest(value) {
    this.request.reporter.password = "";
    this.request.reporter.email = "";
    this.request.assignee.id = null;
    this.request.assignee.password = "";
    this.request.assignee.email = "";
    this.request.assignee.firstName = "";
    this.request.assignee.lastName = "";
    this.request.parentId = null;

    this.request.reporter.role = this.role;
    this.request.assignee.role = this.role;

    this.requestService.update(this.request)
      .subscribe(() => {
        this.modal.close();
        this.toastr.success("Request updated", "Success")
      });
  }
}
