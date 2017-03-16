import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {Routes, RouterModule} from "@angular/router";
import {UserTableDeactivatedComponent} from "./user-table-deactivated.component";
import {ActivateUserComponent} from "./user-activate/activate-user.component";
import {FormTemplateModule} from "../../shared/form-template/form-template.module";
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";
import {DateParseModule} from "../../util/date-parser/date-parse.module";

const routes: Routes = [
  {path: '', component: UserTableDeactivatedComponent}
];

@NgModule({
  imports: [
    DateParseModule,
    CommonModule,
    RouterModule.forChild(routes),
    FormTemplateModule,
    Ng2Bs3ModalModule,
    ReactiveFormsModule
  ],
  declarations: [
    UserTableDeactivatedComponent,
    ActivateUserComponent
  ],
  exports: [
    UserTableDeactivatedComponent,
    ActivateUserComponent
  ],
  providers: []
})
export class UserTableDeactivatedModule {
}
