import {CommonModule} from "@angular/common";
import {NgModule} from "@angular/core";
import {FormTemplateModule} from "../../shared/form-template/form-template.module";
import {RequestModule} from "../../shared/request/request.module";
import {DateParseModule} from "../../util/date-parser/date-parse.module";
import {DeleteRequestComponent} from "./request-delete/delete-request.component";
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";
import {RequestTable} from "./request-table.component";
import {RouterModule} from "@angular/router";
import {PipeModule} from "../../pipes/pipe.module";
import {PaginationModule} from "ng2-bootstrap";
import {AssignRequestComponent} from "./request-assign/assign-request.component";
import {JoinRequestComponent} from "./request-join/join-request.component";
import {CloseRequestComponent} from "./request-close/close-request.component";
import {ReopenRequestComponent} from "./request-reopen/reopen-request.component";
import {PriorityMarkerModule} from "../../util/priority-marker/priority-marker.module";
import {ProgressMarkerModule} from "../../util/progress-marker/progress-marker.module";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    DateParseModule,
    PriorityMarkerModule,
    ProgressMarkerModule,
    CommonModule,
    FormTemplateModule,
    RequestModule,
    ReactiveFormsModule,
    RouterModule,
    PaginationModule.forRoot(),
    PipeModule.forRoot(),
    Ng2Bs3ModalModule,
    FormsModule,
  ],
  declarations: [
    RequestTable,
    AssignRequestComponent,
    DeleteRequestComponent,
    CloseRequestComponent,
    JoinRequestComponent,
    ReopenRequestComponent
  ],
  exports: [
    RequestTable,
    AssignRequestComponent,
    CloseRequestComponent,
    DeleteRequestComponent,
    JoinRequestComponent,
    ReopenRequestComponent,
  ]
})
export class BasicRequestTableModule{}
