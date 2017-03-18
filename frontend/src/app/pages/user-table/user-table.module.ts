import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {Routes, RouterModule} from "@angular/router";
import {AddUserComponent} from "./user-add/add-user.component";
import {UserTableComponent} from "./user-table.component";
import {DeleteUserComponent} from "./user-delete/delete-user.component";
import {FormTemplateModule} from "../../shared/form-template/form-template.module";
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";
import {UserFilterPipe} from "../../pipes/user-filter.pipe";
import {UserSortPipe} from "../../pipes/user-sort.pipe";

const routes: Routes = [
  {path: '', component: UserTableComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormTemplateModule,
    Ng2Bs3ModalModule,
    ReactiveFormsModule,
    FormsModule
  ],
  declarations: [
    UserTableComponent,
    AddUserComponent,
    DeleteUserComponent,
    UserFilterPipe,
    UserSortPipe
  ],
  exports: [
    UserTableComponent,
    AddUserComponent,
    DeleteUserComponent
  ],
  providers: []
})
export class UserTableModule {
}
