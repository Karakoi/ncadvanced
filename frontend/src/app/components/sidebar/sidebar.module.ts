import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {GravatarSharedModule} from "../../pages/shared/sharedGravatar.module";
import {SideBarComponent} from "./sidebar.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    GravatarSharedModule
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
