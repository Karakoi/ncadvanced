import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {InlineEditorModule} from "ng2-inline-editor";
import {UserProfileComponent} from "./user-profile.component";
import {GravatarModule} from "../../shared/gravatar/gravatar.module";
import {Ng2GoogleChartsModule} from "ng2-google-charts";

const routes: Routes = [
  {path: '', component: UserProfileComponent},
];

@NgModule({
  imports: [
    FormsModule,
    GravatarModule,
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    InlineEditorModule,
    Ng2GoogleChartsModule
  ],
  declarations: [
    UserProfileComponent
  ],
  exports: [
    UserProfileComponent
  ]
})
export class UserProfileModule {
}
