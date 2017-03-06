import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {TestModule} from "../../pages/shared/SharedGravatar.module";
import {SideBarComponent} from "./sidebar.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TestModule
  ],
  declarations: [
    SideBarComponent
  ],
  exports: [
    SideBarComponent
  ]
})
export class SideBarModule {
}
