import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {NgModule} from "@angular/core/src/metadata/ng_module";
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




@NgModule({
  imports: [
    DateParseModule,
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
    JoinRequestComponent
  ],
  exports: [
    RequestTable,
    AssignRequestComponent,
    CloseRequestComponent,
    DeleteRequestComponent,
    JoinRequestComponent
  ]
})
export class  BasicRequestTableModule {
}
