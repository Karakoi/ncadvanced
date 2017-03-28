import {RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {AdminComponent} from "./admin/admin.component";
import {ManagerComponent} from "./manager/manager.component";
import {homeRoutes} from "./home.routes";
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";
import {DateParseModule} from "../../util/date-parser/date-parse.module";
import {Ng2GoogleChartsModule} from "ng2-google-charts";
import {FormTemplateModule} from "../../shared/form-template/form-template.module";
import {RequestModule} from "../../shared/request/request.module";
import {RequestFormComponent} from "./request-form/request-form.component";
import {BasicRequestTableModule} from "../../components/request-table/request-table.module";
import {DropdownModule} from "ng2-bootstrap";

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(homeRoutes),
    ReactiveFormsModule,
    Ng2Bs3ModalModule,
    DateParseModule,
    Ng2GoogleChartsModule,
    FormTemplateModule,
    FormsModule,
    RequestModule,
    BasicRequestTableModule,
    Ng2GoogleChartsModule,
    DropdownModule.forRoot()
  ],
  declarations: [
    AdminComponent,
    ManagerComponent,
    RequestFormComponent,
  ],
  exports: [
    AdminComponent,
    ManagerComponent,
    RequestFormComponent,
  ]
})
export class HomeModule {
}
