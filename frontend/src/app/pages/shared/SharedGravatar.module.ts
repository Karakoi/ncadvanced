import {NgModule} from "@angular/core";
import {GravatarComponent} from "../../components/gravatar/gravatar.component";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    GravatarComponent
  ],
  exports: [
    GravatarComponent,
    CommonModule,
    FormsModule
  ]
})
export class TestModule {
}
