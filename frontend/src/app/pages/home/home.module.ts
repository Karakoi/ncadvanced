import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {HomeComponent} from "./home.component";
import {RequestFormComponent} from "./request-form/request-form.component";
import {RequestFormDirective} from "../../directive/request-form.directive";

const routes: Routes = [
  {path: '', component: HomeComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  declarations: [
    HomeComponent,
    RequestFormComponent,
    RequestFormDirective
  ],
  exports: [
    HomeComponent,
    RequestFormComponent
  ]
})
export class HomeModule {
}