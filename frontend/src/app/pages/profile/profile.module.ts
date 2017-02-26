import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {NgModule} from "@angular/core/src/metadata/ng_module";
import {Routes, RouterModule} from "@angular/router";
import {EditProfileComponent} from "./edit-profile/edit-profile.component";
import {ProfileComponent} from "./profile.component";

const routes: Routes = [
  {
    path: '',
    component: ProfileComponent
  },
  {
    path: 'edit',
    component: EditProfileComponent
  }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  declarations: [
    ProfileComponent,
    EditProfileComponent
  ],
  exports: [
    ProfileComponent,
    EditProfileComponent
  ]
})
export class ProfileModule {
}