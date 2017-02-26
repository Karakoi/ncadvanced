import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {EditProfileComponent} from "./editProfile.component";

const routes: Routes = [
  {path: '', component: EditProfileComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  declarations: [
    EditProfileComponent
  ],
  exports: [
    EditProfileComponent
  ]
})
export class EditProfileModule {
}
