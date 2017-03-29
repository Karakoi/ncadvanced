import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {PriorityMarkerComponent} from "./priority-marker.component";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    PriorityMarkerComponent
  ],
  exports: [
    PriorityMarkerComponent
  ]
})
export class PriorityMarkerModule {
}
