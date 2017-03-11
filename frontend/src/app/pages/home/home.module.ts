import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {UserComponent} from "./user/user.component";
import {AdminComponent} from "./admin/admin.component";
import {ManagerComponent} from "./manager/manager.component";
import {homeRoutes} from "./home.routes";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(homeRoutes),
    ReactiveFormsModule
  ],
  declarations: [
    UserComponent,
    AdminComponent,
    ManagerComponent
  ],
  exports: [
    UserComponent,
    AdminComponent,
    ManagerComponent
  ]
})
export class HomeModule {
}