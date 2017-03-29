import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {RequestProfileComponent} from "./request-profile.component";
import {InlineEditorModule} from "ng2-inline-editor";
import {DateParseModule} from "../../util/date-parser/date-parse.module";
import {DeleteSubRequestComponent} from "./sub-request-delete/delete-sub-request.component";
import {FormTemplateModule} from "../../shared/form-template/form-template.module";
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";
import {AddSubRequestComponent} from "./sub-request-add/add-sub-request.component";
import {PriorityMarkerModule} from "../../util/priority-marker/priority-marker.module";
import {ProgressMarkerModule} from "../../util/progress-marker/progress-marker.module";

const routes: Routes = [
  {path: '', component: RequestProfileComponent},
];

@NgModule({
  imports: [
    PriorityMarkerModule,
    ProgressMarkerModule,
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    InlineEditorModule,
    DateParseModule,
    Ng2Bs3ModalModule
  ],
  declarations: [
    RequestProfileComponent,
    DeleteSubRequestComponent,
    AddSubRequestComponent
  ],
  exports: [
    RequestProfileComponent,
    DeleteSubRequestComponent,
    AddSubRequestComponent
  ]
})
export class RequestProfileModule {
}
