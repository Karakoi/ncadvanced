import {Component, ViewChild} from "@angular/core";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../model/request.model";

@Component({
  selector: 'request-details',
  templateUrl: 'request-details.component.html',
  styleUrls: ['request-details.component.css']
})
export class RequestDetailsComponent {
  showDescription: boolean = true;
  showHistory: boolean = true;
  public request: Request;

  @ViewChild('requestDetailsModal')
  modal: ModalComponent;

  changeShowDescription() {
    this.showDescription = !this.showDescription;
  }

  changeShowHistory() {
    this.showHistory = !this.showHistory;
  }
}
