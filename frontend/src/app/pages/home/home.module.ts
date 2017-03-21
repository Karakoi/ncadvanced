import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {AdminComponent} from "./admin/admin.component";
import {ManagerComponent} from "./manager/manager.component";
import {Ng2SmartTableModule} from "ng2-smart-table";
import {homeRoutes} from "./home.routes";
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";
import {DateParseModule} from "../../util/date-parser/date-parse.module";
import {Ng2GoogleChartsModule} from "ng2-google-charts";
import {BasicRequestTableModule} from "../../components/request-table/request-table.module";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(homeRoutes),
    ReactiveFormsModule,
    Ng2Bs3ModalModule,
    DateParseModule,
    Ng2SmartTableModule,
    BasicRequestTableModule,
    Ng2GoogleChartsModule
  ],
  declarations: [
    AdminComponent,
    ManagerComponent,
  ],
  exports: [
    AdminComponent,
    ManagerComponent,
  ]
})
export class HomeModule {
}
