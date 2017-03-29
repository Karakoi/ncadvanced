import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ProgressMarkerComponent} from "./progress-marker.component";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    ProgressMarkerComponent
  ],
  exports: [
    ProgressMarkerComponent
  ]
})
export class ProgressMarkerModule {
}
