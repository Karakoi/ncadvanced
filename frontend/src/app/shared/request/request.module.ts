import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RequestDetailsComponent} from "./request-details/request-details.component";
import {RequestFormComponent} from "./request-form/request-form.component";
import {Ng2Bs3ModalModule} from "ng2-bs3-modal/ng2-bs3-modal";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    Ng2Bs3ModalModule
  ],
  declarations: [
    RequestDetailsComponent,
    RequestFormComponent
  ],
  exports: [
    RequestDetailsComponent,
    RequestFormComponent
  ]
})
export class RequestModule {
}
