import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {ProfileComponent} from "./profile.component";
import {GravatarComponent} from "./gravatar/gravatar.component";

const routes: Routes = [
  {path: '', component: ProfileComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    ProfileComponent,
    GravatarComponent
  ],
  exports: [
    ProfileComponent,
    GravatarComponent
  ]
})
export class ProfileModule {
}
