import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule} from "@angular/forms";
import {RecoverComponent} from "./recover.component";

const routes: Routes = [
  {path: '', component: RecoverComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  declarations: [
    RecoverComponent
  ],
  exports: [
    RecoverComponent
  ]
})
export class RecoverModule {
}