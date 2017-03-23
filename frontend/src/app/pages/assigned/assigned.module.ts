import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {AssignedComponent} from "./assigned.component";
import {GravatarModule} from "../../shared/gravatar/gravatar.module";
import {DateParseModule} from "../../util/date-parser/date-parse.module";
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";
import {BasicRequestTableModule} from "../../components/request-table/request-table.module";

const routes: Routes = [
  {path: '', component: AssignedComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    DateParseModule,
    ReactiveFormsModule,
    BasicRequestTableModule,
    GravatarModule,
    Ng2Bs3ModalModule
  ],
  declarations: [
    AssignedComponent,
  ],
  exports: [
    AssignedComponent,
  ]
})
export class AssignedModule {
}
