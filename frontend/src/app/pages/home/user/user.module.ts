declare const Buffer;
import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {Ng2SmartTableModule} from "ng2-smart-table";
import {PaginationModule} from 'ng2-bootstrap';
import {userRouts} from "./user.routes";
import {ActiveRequest} from "./userRequests/active-request.component";
import {ClosedRequest} from "./userClosedRequests/closed-request.component";


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(userRouts),
    ReactiveFormsModule,
    Ng2SmartTableModule,
    PaginationModule.forRoot()
  ],
  declarations: [
    ActiveRequest,
    ClosedRequest
  ],
  exports: [
    ActiveRequest,
    ClosedRequest
  ]
})
export class UserModule {
}
