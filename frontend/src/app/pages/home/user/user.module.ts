import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {PaginationModule} from 'ng2-bootstrap';
import {userRouts} from "./user.routes";
import {ActiveRequest} from "./userRequests/active-request.component";
import {ClosedRequest} from "./userClosedRequests/closed-request.component";
import { ModalModule } from 'ng2-bootstrap';
import {BasicRequestTableModule} from "../../../components/request-table/request-table.module";



@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(userRouts),
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    BasicRequestTableModule,
    PaginationModule.forRoot(),
    ModalModule.forRoot(),
  ],
  declarations: [
    ActiveRequest,
    ClosedRequest,
  ],
  exports: [
    ActiveRequest,
    ClosedRequest
  ]
})
export class UserModule {
}
