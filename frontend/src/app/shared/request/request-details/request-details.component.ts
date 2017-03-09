import {Component, ViewChild} from "@angular/core";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";
import {AuthService} from "../../../service/auth.service";
import {Role} from "../../../model/role.model";

@Component({
  selector: 'request-details',
  templateUrl: 'request-details.component.html',
  styleUrls: ['request-details.component.css']
})
export class RequestDetailsComponent {
  showDescription:boolean = true;
  showHistory:boolean = true;
  public request:Request;
  role:Role;

  constructor(private requestService:RequestService,
              private authService:AuthService) {
    this.role = {
      id: 10,
      name: "admin"
    };
  }

  @ViewChild('requestDetailsModal')
  modal:ModalComponent;

  changeShowDescription() {
    this.showDescription = !this.showDescription;
  }

  changeShowHistory() {
    this.showHistory = !this.showHistory;
  }

  updateRequest(value) {
    console.log(this.request);
    this.request.reporter.password = "";
    this.request.reporter.email = "";
    this.request.assignee.password = "";
    this.request.assignee.email = "";
    this.request.parentId = null;

    this.request.reporter.role = this.role;
    this.request.assignee.role = this.role;

    this.requestService.update(this.request)
      .subscribe((resp) => console.log(resp));
  }
}
