import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {RequestFormComponent} from "./request-form.component";

const routes: Routes = [
  {path: '', component: RequestFormComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  declarations: [
    RequestFormComponent
  ],
  exports: [
    RequestFormComponent
  ]
})
export class RequestFormModule {
}