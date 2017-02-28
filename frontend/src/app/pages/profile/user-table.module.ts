import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {Routes, RouterModule} from "@angular/router";
import {AddUserComponent} from "../../../../../../src/app/pages/profile/user-add/user.component";
import {UserTable} from "./user-table.component";

const routes: Routes = [
  {
    path: '',
    component: UserTableModule
  },
  {

  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  declarations: [
    UserTable,
    AddUserComponent
  ],
  exports: [
    UserTable,
    AddUserComponent,
  ]
})
export class UserTableModule {
}
