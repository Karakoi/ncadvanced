import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {UserProfileComponent} from "./user-profile.component";

const routes: Routes = [
  {path: '', component: UserProfileComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
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
