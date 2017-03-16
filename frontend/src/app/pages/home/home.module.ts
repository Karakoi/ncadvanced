import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {AdminComponent} from "./admin/admin.component";
import {ManagerComponent} from "./manager/manager.component";
import {homeRoutes} from "./home.routes";
import {AssignRequestComponent} from "../../pages/home/manager/request-assign/assign-request.component"
import {JoinRequestComponent} from "../../pages/home/manager/request-join/join-request.component"
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(homeRoutes),
    ReactiveFormsModule,
    Ng2Bs3ModalModule
  ],
  declarations: [
    UserComponent,
    AssignRequestComponent,
    JoinRequestComponent,
    AdminComponent,
    ManagerComponent,
  ],
  exports: [
    UserComponent,
    AssignRequestComponent,
    JoinRequestComponent,
    AdminComponent,
    ManagerComponent,
  ]
})
export class HomeModule {
}
