import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {Routes, RouterModule} from "@angular/router";
import {RequestTableComponent} from "./request-table.component";
import {RequestDetailsComponent} from "./request-details/request-details.component";
import {RequestDetailDirective} from "../../directive/request-detail.directive";

const routes: Routes = [
  {path: '', component: RequestTableComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  declarations: [
    RequestTableComponent,
    RequestDetailsComponent,
    RequestDetailDirective
  ],
  exports: [
    RequestTableComponent,
    RequestDetailsComponent,
    RequestDetailDirective
  ]
})
export class RequestTableModule {
}
