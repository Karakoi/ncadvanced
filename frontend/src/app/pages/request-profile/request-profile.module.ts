import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {RequestProfileComponent} from "./request-profile.component";
import {InlineEditorModule} from "ng2-inline-editor";

const routes: Routes = [
  {path: '', component: RequestProfileComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule,
    ReactiveFormsModule,
    InlineEditorModule
  ],
  declarations: [
    RequestProfileComponent
  ],
  exports: [
    RequestProfileComponent
  ]
})
export class RequestProfileModule {
}
