import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {TimeParseComponent} from "./time-parse.component";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    TimeParseComponent
  ],
  exports: [
    TimeParseComponent
  ]
})
export class TimeParseModule {
}
