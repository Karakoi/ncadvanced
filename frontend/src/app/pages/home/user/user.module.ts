import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {Ng2SmartTableModule} from "ng2-smart-table";
import {PaginationModule} from 'ng2-bootstrap';
import {userRouts} from "./user.routes";
import {ActiveRequest} from "./userRequests/active-request.component";
import {ClosedRequest} from "./userClosedRequests/closed-request.component";
import { ModalModule } from 'ng2-bootstrap';
import {CreateEmployeeRequest} from "./userAddRequest/create-request.component";
import {BasicRequestTableModule} from "../../../components/request-table/request-table.module";



@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(userRouts),
    ReactiveFormsModule,
    Ng2SmartTableModule,
    FormsModule,
    BasicRequestTableModule,
    PaginationModule.forRoot(),
    ModalModule.forRoot(),
  ],
  declarations: [
    ActiveRequest,
    ClosedRequest,
    CreateEmployeeRequest,
  ],
  exports: [
    ActiveRequest,
    CreateEmployeeRequest,
    ClosedRequest
  ]
})
export class UserModule {
}
