import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {Routes, RouterModule} from "@angular/router";
import {RequestTableComponent} from "./request-table.component";
import {FormTemplateModule} from "../../shared/form-template/form-template.module";
import {RequestModule} from "../../shared/request/request.module";

const routes: Routes = [
  {path: '', component: RequestTableComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormTemplateModule,
    RequestModule,
    ReactiveFormsModule
  ],
  declarations: [
    RequestTableComponent
  ],
  exports: [
    RequestTableComponent
  ]
})
export class RequestTableModule {
}
