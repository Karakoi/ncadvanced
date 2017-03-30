import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {PaginationModule} from 'ng2-bootstrap';
import {ModalModule} from 'ng2-bootstrap';
import {BasicRequestTableModule} from "../../../components/request-table/request-table.module";
import {ManagerComponent} from "./manager.component";
import {ManagerRoutes} from "./manager.routes";


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(ManagerRoutes),
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    BasicRequestTableModule,
    PaginationModule.forRoot(),
    ModalModule.forRoot(),
  ],
  declarations: [
    ManagerComponent
  ],
  exports: [
    ManagerComponent
  ]
})
export class ManagerModule {
}
