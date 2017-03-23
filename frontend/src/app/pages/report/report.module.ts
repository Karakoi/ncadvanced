import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {ReactiveFormsModule, FormsModule} from "@angular/forms";
import {ReportComponent} from "./report.component";
import {Routes, RouterModule} from "@angular/router";
import {BarChartModule} from "../../shared/bar-chart/bar-chart.module";
import {LineChartModule} from "../../shared/line-chart/line-chart.module";
import {BarChartComponent} from "../../shared/bar-chart/bar-chart.component";

const routes: Routes = [
  {path: 'reports', component: ReportComponent},
];


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    BarChartModule,
    LineChartModule
  ],
  declarations: [
    ReportComponent
    // BarChartComponent
  ],
  exports: [
    ReportComponent
  ]
})
export class ReportModule {
}
