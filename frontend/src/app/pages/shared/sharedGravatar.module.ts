import {NgModule} from "@angular/core";
import {GravatarComponent} from "../../components/gravatar/gravatar.component";
import {CommonModule} from "@angular/common";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    GravatarComponent
  ],
  exports: [
    GravatarComponent
  ]
})
export class GravatarSharedModule {
}
