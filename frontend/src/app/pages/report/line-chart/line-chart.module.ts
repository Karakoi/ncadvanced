import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {LineChartComponent} from "./line-chart.component";
import {ChartsModule} from "ng2-charts";

@NgModule({
  imports: [
    CommonModule,
    ChartsModule
  ],
  declarations: [
    LineChartComponent
  ],
  exports: [
    LineChartComponent
  ]
})
export class LineChartModule {
}
